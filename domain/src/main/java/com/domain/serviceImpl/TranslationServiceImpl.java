package com.domain.serviceImpl;

import com.domain.model.Label;
import com.domain.model.LabelTranslation;
import com.domain.repo.CustomerRepository;
import com.domain.repo.LabelRepository;
import com.domain.repo.LabelTranslationRepository;
import com.domain.service.TranslationService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TranslationServiceImpl implements TranslationService {

    private final CustomerRepository customerRepository;
    private final LabelRepository labelRepository;
    private final LabelTranslationRepository labelTranslationRepository;

    public TranslationServiceImpl(CustomerRepository customerRepository,
                                  LabelRepository labelRepository,
                                  LabelTranslationRepository labelTranslationRepository) {
        this.customerRepository = customerRepository;
        this.labelRepository = labelRepository;
        this.labelTranslationRepository = labelTranslationRepository;
    }

    @Override
    public Map<String, String> getTranslations(String customerUid, String langCode) {
        UUID cuid = UUID.fromString(customerUid);
        List<Label> labels = labelRepository.findByCustomer_Cuid(cuid);

        Map<String, String> translations = new HashMap<>();

        for (Label label : labels) {
            // Use labelKey instead of labelName to match what's in label_translation.label_name
            List<LabelTranslation> translationsList = labelTranslationRepository
                    .findByLabel_LabelKeyAndLanguageCode(label.getLabelKey(), langCode);

            if (!translationsList.isEmpty()) {
                // Just pick the latest or first — customize if needed
                LabelTranslation trans = translationsList.get(0);
                translations.put(label.getLabelKey(), trans.getLabelTranslated());

                System.out.println("Label Key: " + label.getLabelKey());
                System.out.println("Label Translated: " + trans.getLabelTranslated());
            } else {
                System.out.println("No translation found for: " + label.getLabelKey() + " in " + langCode);
            }

        }

        System.out.println("Final Translations Map: " + translations);
        return translations;
    }


}

