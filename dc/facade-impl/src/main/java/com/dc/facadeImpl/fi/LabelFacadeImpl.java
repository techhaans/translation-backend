package com.dc.facadeImpl.fi;

import com.domain.service.LabelService;
import com.dc.facade.fd.LabelFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LabelFacadeImpl implements LabelFacade {

    @Autowired
    private LabelService labelService;

    @Override
    public void processLabels(Integer customerId, String defaultLanguage, List<String> languageList, Map<String, String> labelData) {
        labelService.createOrUpdateLabels(customerId, defaultLanguage, languageList, labelData);
    }
}
