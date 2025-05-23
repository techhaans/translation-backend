import os
import re
import json
import requests
import subprocess

def safe_print(msg):
    try:
        print(msg)
    except UnicodeEncodeError:
        print(msg.encode("utf-8", errors="ignore").decode("utf-8"))

def load_config():
    base_dir = os.path.dirname(os.path.abspath(__file__))
    config_path = os.path.join(base_dir, "config.json")
    try:
        with open(config_path, "r", encoding="utf-8") as f:
            return json.load(f)
    except Exception as e:
        safe_print(f"[load_config] Failed to read config.json: {e}")
        exit(1)

def extract_text_from_html(file_path, prefix):
    translations = {}
    label_counter = 1
    placeholder_counter = 1

    with open(file_path, "r", encoding="utf-8") as f:
        html = f.read()

    safe_print(f"[extract_text_from_html] File: {file_path}")

    label_matches = re.findall(r'<(label|button|span|p|h[1-6])[^>]*?>(.*?)</\1>', html, re.DOTALL)
    for tag, text in label_matches:
        text = text.strip()
        if not text:
            continue
        key = f"{prefix}.label_{label_counter}"
        translations[key] = text
        label_counter += 1

    placeholder_matches = re.findall(r'<(input|textarea)[^>]*?placeholder="([^"]+)"', html)
    for tag, placeholder in placeholder_matches:
        key = f"{prefix}.placeholder_{placeholder_counter}"
        translations[key] = placeholder
        placeholder_counter += 1

    safe_print(f"[extract_text_from_html] Found labels: {json.dumps(translations, indent=2)}")
    return translations

def fetch_customer_languages(customer_uid, backend_url, endpoint):
    try:
        url = f"{backend_url}{endpoint}/{customer_uid}"
        resp = requests.get(url)
        resp.raise_for_status()
        return resp.json()
    except Exception as e:
        safe_print("Failed to fetch customer languages: " + str(e))
        return []

def inject_dropdown(html_content, languages):
    options_html = ""
    for lang in languages:
        code = lang.get("languageCode")
        name = lang.get("languageName")
        selected = " selected" if lang.get("default", False) else ""
        options_html += f'    <option value="{code}"{selected}>{name}</option>\n'

    dropdown_html = f'''
<select id="language">
{options_html}</select>
'''
    if "<body" in html_content:
        return re.sub(r'(<body[^>]*?>)', r'\1\n' + dropdown_html, html_content, count=1, flags=re.IGNORECASE)
    else:
        return dropdown_html + html_content

def update_html_file_with_i18n(file_path, prefix, translations, inject_dropdown_flag=False, languages=None, translation_script="i18n/translation.js"):
    with open(file_path, "r", encoding="utf-8") as f:
        html = f.read()

    label_counter = 1
    placeholder_counter = 1

    def replace_label(match):
        nonlocal label_counter
        tag, content = match.group(1), match.group(2).strip()
        key = f"{prefix}.label_{label_counter}"
        label_counter += 1
        return f'<{tag} data-i18n="{key}"></{tag}>'

    def replace_placeholder(match):
        nonlocal placeholder_counter
        key = f"{prefix}.placeholder_{placeholder_counter}"
        placeholder_counter += 1
        return re.sub(r'placeholder="[^"]+"', f'data-i18n="{key}"', match.group(0))

    html = re.sub(r'<(label|button|span|p|h[1-6])[^>]*?>(.*?)</\1>', replace_label, html, flags=re.DOTALL)
    html = re.sub(r'<(input|textarea)[^>]*?placeholder="([^"]+)"', replace_placeholder, html)

    if translation_script not in html:
        html = re.sub(r'(</body\s*>)', f'<script src="{translation_script}"></script>\n\\1', html, flags=re.IGNORECASE)

    if inject_dropdown_flag and languages:
        html = inject_dropdown(html, languages)

    with open(file_path, "w", encoding="utf-8") as f:
        f.write(html)

def write_js_files(i18n_folder_path, labels_by_lang):
    os.makedirs(i18n_folder_path, exist_ok=True)
    for lang_code, labels in labels_by_lang.items():
        js_file = os.path.join(i18n_folder_path, f"_{lang_code}.js")
        with open(js_file, "w", encoding="utf-8") as f:
            f.write(f"var translations_{lang_code} = {{\n")
            for key, value in labels.items():
                f.write(f'    "{key}": "{value}",\n')
            f.write("};\n")

def run_translation_workflow(config):
    repo_path = config["repoPath"]
    customer_uid = config["customerUId"]
    package_folder = config["packageFolder"]
    header_file_name = config["headerFileName"]
    backend_url = config["backendUrl"]
    label_post_endpoint = config["labelPostEndpoint"]
    language_fetch_endpoint = config["languageFetchEndpoint"]
    translation_script = config["translationScriptPath"]
    i18n_folder = os.path.join(os.path.dirname(os.path.abspath(__file__)), config["i18nFolderName"])
    commit_message = config.get("commitMessage", "Update i18n translations")

    package_path = os.path.join(repo_path, package_folder)
    html_files = [f for f in os.listdir(package_path) if f.endswith(".html")]

    extracted_labels = {}
    customer_lang_response = fetch_customer_languages(customer_uid, backend_url, language_fetch_endpoint)
    customer_languages = customer_lang_response.get("data", [])  # Extract only the list

    safe_print(f"Customer Languages: {json.dumps(customer_languages, indent=2)}")

    for html_file in html_files:
        prefix = os.path.splitext(html_file)[0]
        file_path = os.path.join(package_path, html_file)
        safe_print(f"Scanning HTML file: {html_file}")

        labels = extract_text_from_html(file_path, prefix)
        extracted_labels.update(labels)

        is_header = html_file.lower() == header_file_name.lower()
        update_html_file_with_i18n(
            file_path, prefix, labels,
            inject_dropdown_flag=is_header,
            languages=customer_languages,
            translation_script=translation_script
        )
        safe_print(f"Processed: {html_file}")

    if not extracted_labels:
        safe_print("No labels found.")
        return

    safe_print("Sending extracted label payload to backend:")
    safe_print(json.dumps(extracted_labels, indent=2))

    headers = {
        "customerUId": customer_uid,
        "Content-Type": "application/json"
    }

    try:
        response = requests.post(f"{backend_url}{label_post_endpoint}", json=extracted_labels, headers=headers)
        response.raise_for_status()
        safe_print("Label payload posted.")
    except Exception as e:
        safe_print("Error posting labels: " + str(e))
        return

    try:
        resp_json = response.json()
        labels_by_lang = {l["languageCode"]: l["translations"] for l in resp_json["languages"]}
        write_js_files(i18n_folder, labels_by_lang)
        safe_print("i18n JS files generated.")
    except Exception as e:
        safe_print("JS generation failed: " + str(e))

    try:
        subprocess.run(["git", "-C", repo_path, "add", "."], check=True)
        subprocess.run(["git", "-C", repo_path, "commit", "-m", commit_message], check=True)
        subprocess.run(["git", "-C", repo_path, "push"], check=True)
        safe_print("Git commit and push complete.")
    except subprocess.CalledProcessError as e:
        safe_print("Git error: " + str(e))

if __name__ == "__main__":
    config = load_config()
    run_translation_workflow(config)
