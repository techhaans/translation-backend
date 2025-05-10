import os
import re
import json
import sys
import requests

# ==== Config ====
if len(sys.argv) < 2:
    print("Please provide the path to the cloned repo. Example:")
    print("python extract_i18n_template.py temp/<uuid>")
    sys.exit(1)

html_folder = sys.argv[1]
script_dir = os.path.dirname(os.path.abspath(__file__))
translation_dir = os.path.join(script_dir, "i18n")
os.makedirs(translation_dir, exist_ok=True)

translation_file = os.path.join(translation_dir, "_en.js")
customer_id = "CUSTOMER_ID_PLACEHOLDER"
default_language = "en"
translation_endpoint = "http://localhost:8082/labels"

headers = {
    "customerUId": "afc3c097-9054-49bc-938c-bfbcbd9ea3c7",
    "accept": "application/json",
    "Content-Type": "application/json"
}

# ==== Step 1: Load existing translations ====
existing_translations = {}
if os.path.exists(translation_file):
    with open(translation_file, "r", encoding="utf-8") as f:
        content = f.read()
        matches = re.findall(r'"([^"]+)":\s*"([^"]+)"', content)
        for k, v in matches:
            existing_translations[k] = v

# ==== Step 2: Process all HTML files ====
all_translations = existing_translations.copy()
html_files = [f for f in os.listdir(html_folder) if f.endswith(".html")]
counter = len(existing_translations) + 1

for html_file in html_files:
    full_path = os.path.join(html_folder, html_file)
    filename_prefix = os.path.splitext(os.path.basename(html_file))[0]

    print(f" Processing {html_file}...")

    with open(full_path, "r", encoding="utf-8") as f:
        html = f.read()

    translations = {}
    found_any = False

    # Match visible labels
    label_matches = re.findall(r'<(label|button|span|p|h[1-6])[^>]*?>(.*?)</\1>', html, re.DOTALL)
    for tag, text in label_matches:
        text = text.strip()
        if not text:
            continue
        key = f"label_{counter}"
        translations[key] = text
        replacement = f'<{tag} data-i18n="{key}"></{tag}>'
        html = re.sub(rf'<{tag}[^>]*?>\s*{re.escape(text)}\s*</{tag}>', replacement, html, count=1)
        counter += 1
        found_any = True

    # Match placeholders
    placeholder_matches = re.findall(r'<(input|textarea)[^>]*?placeholder="([^"]+)"', html)
    for tag, placeholder in placeholder_matches:
        key = f"placeholder_{counter}"
        translations[key] = placeholder
        html = html.replace(f'placeholder="{placeholder}"', f'data-i18n-placeholder="{key}"')
        counter += 1
        found_any = True

    # Inject translation.js if not already included
    if 'translation.js' not in html:
        script_tag = '<script src="translations/translation.js"></script>'
        if '</body>' in html:
            html = html.replace('</body>', script_tag + '\n</body>')
        else:
            html += '\n' + script_tag

    # Save modified HTML
    with open(full_path, "w", encoding="utf-8") as f:
        f.write(html)

    # Add translations with file prefix
    for k, v in translations.items():
        all_translations[f"{filename_prefix}.{k}"] = v

    if not found_any:
        print(f" No translatable content found in {html_file}.")

# ==== Step 3: Send POST request ====
payload = all_translations

try:
    print("Sending Payload:")
    print(json.dumps(payload, indent=2))
    res = requests.post(translation_endpoint, json=payload, headers=headers)
    res.raise_for_status()
    response_data = res.json()
    print("Received response from API")
    print(json.dumps(response_data, indent=2))
except Exception as e:
    print("Failed to reach API. Error:", e)
    response_data = {
        "defaultLanguageCode": default_language,
        "languages": [
            {
                "languageCode": default_language,
                "translations": payload
            }
        ]
    }

# ==== Step 4: Generate JS files for each language ====
langs = response_data.get("languages", [])
for lang in langs:
    lang_code = lang.get("languageCode")
    lang_translations = lang.get("translations", {})
    file_path = os.path.join(translation_dir, f"_{lang_code}.js")
    with open(file_path, "w", encoding="utf-8") as f:
        f.write(f"var translations_{lang_code} = {{\n")
        for full_key, value in lang_translations.items():
            simple_key = full_key.split('.')[-1]
            f.write(f'    "{simple_key}": "{value}",\n')
        f.write("};\n")
    print(f" Generated {file_path}")
