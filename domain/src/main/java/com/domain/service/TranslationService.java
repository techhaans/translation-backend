package com.domain.service;

import java.util.Map;

public interface TranslationService {
    Map<String, String> getTranslations(String customerUid, String langCode);
}
