package com.dc.facadeImpl.fi;

import com.dc.facade.fd.LanguageFacade;
import com.domain.model.Language;
import com.domain.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LanguageFacadeImpl implements LanguageFacade {

    @Autowired
    private LanguageService LanguageService;
    @Override
    public List<Language> getAllLanguages() {
        return LanguageService.getAllLanguages();
    }
}
