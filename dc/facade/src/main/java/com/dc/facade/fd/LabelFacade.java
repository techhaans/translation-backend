package com.dc.facade.fd;

import java.util.List;
import java.util.Map;

public interface LabelFacade {
    void processLabels(Integer customerId, String defaultLanguage, List<String> languageList, Map<String, String> labelData);
}
