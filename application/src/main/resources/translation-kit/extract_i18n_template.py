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

def fetch_customer_languages(customer_uid):
    try:
        url = f"http://localhost:8082/customer/languages/{customer_uid}"
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

def update_html_file_with_i18n(file_path, prefix, translations, inject_dropdown_flag=False, languages=None):
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

    if 'i18n/translation.js' not in html:
        html = re.sub(r'(</body\s*>)', r'<script src="i18n/translation.js"></script>\n\1', html, flags=re.IGNORECASE)

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

def run_translation_workflow(repo_path, customer_uid, package_folder):
    package_path = os.path.join(repo_path, package_folder)
    html_files = [f for f in os.listdir(package_path) if f.endswith(".html")]

    extracted_labels = {}
    customer_languages = fetch_customer_languages(customer_uid)

    safe_print(f"Customer Languages: {json.dumps(customer_languages, indent=2)}")

    for html_file in html_files:
        prefix = os.path.splitext(html_file)[0]
        file_path = os.path.join(package_path, html_file)
        safe_print(f"Scanning HTML file: {html_file}")

        labels = extract_text_from_html(file_path, prefix)
        safe_print(f"Extracted labels from {html_file}: {json.dumps(labels, indent=2)}")

        extracted_labels.update(labels)

        is_header = html_file.lower() == "header.html"
        update_html_file_with_i18n(
            file_path, prefix, labels,
            inject_dropdown_flag=is_header,
            languages=customer_languages
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
        response = requests.post("http://localhost:8082/labels", json=extracted_labels, headers=headers)
        response.raise_for_status()
        safe_print("Label payload posted.")
    except Exception as e:
        safe_print("Error posting labels: " + str(e))
        return

    try:
        resp_json = response.json()
        labels_by_lang = {l["languageCode"]: l["translations"] for l in resp_json["languages"]}
        i18n_folder = os.path.join(os.path.dirname(os.path.abspath(__file__)), "i18n")
        write_js_files(i18n_folder, labels_by_lang)
        safe_print("i18n JS files generated.")
    except Exception as e:
        safe_print("JS generation failed: " + str(e))

    try:
        subprocess.run(["git", "-C", repo_path, "add", "."], check=True)
        subprocess.run(["git", "-C", repo_path, "commit", "-m", "Update i18n translations and HTML with dropdown"], check=True)
        subprocess.run(["git", "-C", repo_path, "push"], check=True)
        safe_print("Git commit and push complete.")
    except subprocess.CalledProcessError as e:
        safe_print("Git error: " + str(e))

if __name__ == "__main__":
    run_translation_workflow(
        repo_path=r"D:\new-test4",
        customer_uid="d16c1cd5-7082-4d6a-8ddf-d28217c1258b",
        package_folder="package"
    )
