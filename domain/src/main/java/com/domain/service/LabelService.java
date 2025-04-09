package com.domain.service;

import java.util.List;
import java.util.Map;

public interface LabelService {
    void createOrUpdateLabels(Integer customerId, String defaultLanguage, List<String> languageList, Map<String, String> labelData);
}
