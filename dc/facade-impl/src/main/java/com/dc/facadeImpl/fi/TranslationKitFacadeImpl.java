package com.dc.facadeImpl.fi;

import com.dc.facade.fd.TranslationKitFacade;
import com.domain.service.TranslationKitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;


@Component
//@RequiredArgsConstructor
public class TranslationKitFacadeImpl implements TranslationKitFacade {
    private final TranslationKitService translationKitService;
    public TranslationKitFacadeImpl(TranslationKitService translationKitService) {
        this.translationKitService = translationKitService;
    }

    @Override
    public File prepareTranslationKit(String customerId, String customerUId) throws IOException {
        return translationKitService.prepareTranslationKit(customerId, customerUId);
    }
}
