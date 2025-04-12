package com.domain.serviceImpl;

import com.domain.dto.LabelRequestDTO;
import com.domain.dto.LabelResponseDTO;
import com.domain.dto.LabelTranslationRequestDTO;
import com.domain.dto.LabelTranslationResponseDTO;
import com.domain.model.*;
import com.domain.service.LabelService;
import com.domain.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabelTranslationRepository labelTranslationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LanguagesRepository languagesRepository;

    @Autowired
    private ProofReaderRepository proofReadersRepository;
    @Autowired
    private CustomerLangRepository customerLangRepository;
    private String extractLabelNameFromKey(String labelKey) {
        if (labelKey == null || labelKey.isEmpty()) return "";
        String[] parts = labelKey.split("\\.");
        return parts[parts.length - 1].replace("_", " ");
    }

    @Override
    public LabelResponseDTO createOrUpdateLabels(Integer customerId, Map<String, String> labels) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<CustomerLang> customerLangs = customerLangRepository.findByCustomer_Cid(customerId);
        if (customerLangs.isEmpty()) {
            throw new RuntimeException("No languages found for customer");
        }

        CustomerLang defaultLang = (CustomerLang) customerLangRepository
                .findByCustomer_CidAndIsDefaultTrue(customerId)
                .orElseThrow(() -> new RuntimeException("Default language not set"));

        String defaultLanguageCode = defaultLang.getLanguage().getLanguageKey();

        Map<String, Label> labelMap = labelRepository.findByCustomer_Cid(customerId).stream()
                .collect(Collectors.toMap(Label::getLabelKey, Function.identity()));

        ProofReaders approvedBy = proofReadersRepository.findByRole("ADMIN")
                .orElseThrow(() -> new RuntimeException("No approver found"));

        List<LabelTranslationResponseDTO> responseLanguages = new ArrayList<>();

        for (CustomerLang lang : customerLangs) {
            String langCode = lang.getLanguage().getLanguageKey();
            Map<String, String> translatedMap = new HashMap<>();

            for (Map.Entry<String, String> entry : labels.entrySet()) {
                String labelKey = entry.getKey();
                String value = entry.getValue();

                Label label = labelMap.computeIfAbsent(labelKey, k -> {
                    Label newLabel = new Label();
                    newLabel.setLabelKey(labelKey);
                    newLabel.setLabelName(extractLabelNameFromKey(labelKey));
                    newLabel.setCustomer(customer);
                    newLabel.setCreatedDate(LocalDateTime.now());
                    newLabel.setUpdatedDate(LocalDateTime.now());
                    return labelRepository.save(newLabel);
                });

                LabelTranslation translation = new LabelTranslation();
                translation.setLabel(labelKey);
                translation.setLanguageCode(langCode);
                translation.setLabelTranslated(value); // Will call ChatGPT later
                translation.setStatus("ACTIVE");
                translation.setApprovedBy(approvedBy);
                translation.setCreatedDate(LocalDateTime.now());
                translation.setUpdatedDate(LocalDateTime.now());
                translation.setTranslations("{}");

                labelTranslationRepository.save(translation);
                translatedMap.put(labelKey, value);
            }

            LabelTranslationResponseDTO res = new LabelTranslationResponseDTO();
            res.setLanguageCode(langCode);
            res.setTranslations(translatedMap);
            responseLanguages.add(res);
        }

        LabelResponseDTO response = new LabelResponseDTO();
        response.setCustomerId(customerId);
        response.setDefaultLanguageCode(defaultLanguageCode);
        response.setLanguages(responseLanguages);

        return response;
    }


}
