// ==== Translation Loader ====

let currentLanguage = "en";
let translations = {};
const customerUId = localStorage.getItem("customerUId");  // Get Customer ID dynamically (optional adjustment)

// Set your backend URL
const backendBaseUrl = "http://localhost:8082";

// Set default dropdown ID
const dropdownId = "language";

function setLanguage(langCode) {
    currentLanguage = langCode;
    loadTranslations();
}

function loadTranslations() {
    const existingScript = document.getElementById("translation-script");
    if (existingScript) {
        existingScript.remove(); // Remove previously loaded translation script
    }

    const script = document.createElement("script");
    script.id = "translation-script";
    script.src = `translations/_${currentLanguage}.js`;
    script.onload = () => {
        translations = window[`translations_${currentLanguage}`] || {};
        applyTranslations();
    };
    script.onerror = () => {
        console.error(`Failed to load translations for ${currentLanguage}`);
    };
    document.head.appendChild(script);
}

function applyTranslations() {
    const translatableElements = document.querySelectorAll("[data-i18n]");
    translatableElements.forEach(el => {
        const key = el.getAttribute("data-i18n");
        if (translations[key]) {
            el.textContent = translations[key];
        }
    });

    const placeholderElements = document.querySelectorAll("[data-i18n-placeholder]");
    placeholderElements.forEach(el => {
        const key = el.getAttribute("data-i18n-placeholder");
        if (translations[key]) {
            el.setAttribute("placeholder", translations[key]);
        }
    });
}

function fetchCustomerLanguages() {
    if (!customerUId) {
        console.error("Customer UID not found in localStorage!");
        return;
    }

    fetch(`${backendBaseUrl}/customer/languages/${customerUId}`, {
        headers: {
            "accept": "application/json"
        }
    })
    .then(response => response.json())
    .then(languages => {
        const dropdown = document.getElementById(dropdownId);
        if (!dropdown) {
            console.error(`Dropdown with ID '${dropdownId}' not found!`);
            return;
        }

        // Clear existing options
        dropdown.innerHTML = "";

        languages.forEach(lang => {
            const option = document.createElement("option");
            option.value = lang.code;
            option.text = lang.name;
            dropdown.appendChild(option);
        });

        // Set default language
        if (languages.length > 0) {
            currentLanguage = languages.find(l => l.defaultLanguage)?.code || languages[0].code;
            dropdown.value = currentLanguage;
            loadTranslations();
        }
    })
    .catch(error => {
        console.error("Failed to fetch customer languages:", error);
    });
}

// Initialize everything
window.addEventListener("DOMContentLoaded", () => {
    fetchCustomerLanguages();

    const dropdown = document.getElementById(dropdownId);
    if (dropdown) {
        dropdown.addEventListener("change", (e) => {
            setLanguage(e.target.value);
        });
    }
});
