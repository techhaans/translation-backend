package com.domain.serviceImpl;

import com.domain.dto.LabelResponseDTO;
import com.domain.dto.LabelTranslationResponseDTO;
import com.domain.util.ChatGptTranslator;
import com.domain.model.*;
import com.domain.service.LabelService;
import com.domain.repo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LabelServiceImpl implements LabelService {

    public static final String ADMIN_ROLE = "proof_reader";


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

    @Autowired
    private ChatGptTranslator chatGptTranslator;

    private String extractLabelNameFromKey(String labelKey) {
        if (labelKey == null || labelKey.isEmpty()) return "";
        String[] parts = labelKey.split("\\.");
        return parts[parts.length - 1].replace("_", " ");
    }

    @Override
    public LabelResponseDTO createOrUpdateLabels(UUID customerCuid, Map<String, String> labels) {
        Customer customer = customerRepository.findByCuid(customerCuid)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<CustomerLang> customerLangs = customerLangRepository.findByCustomer_Cuid(customerCuid);
        if (customerLangs.isEmpty()) {
            throw new RuntimeException("No languages found for customer");
        }

        CustomerLang defaultLang = (CustomerLang) customerLangRepository
                .findByCustomer_CuidAndIsDefaultTrue(customerCuid)
                .orElseThrow(() -> new RuntimeException("Default language not set"));

        String defaultLanguageCode = defaultLang.getLanguage().getLanguageKey();

        Map<String, Label> labelMap = labelRepository.findByCustomer_Cuid(customerCuid).stream()
                .collect(Collectors.toMap(Label::getLabelKey, Function.identity()));

        ProofReaders approvedBy = proofReadersRepository.findByRole(ADMIN_ROLE)
                .orElseThrow(() -> new RuntimeException("No approver found"));

        // Save or update labels and default language translations
        for (Map.Entry<String, String> entry : labels.entrySet()) {
            String labelKey = entry.getKey();
            String value = entry.getValue();

            Label label = labelMap.computeIfAbsent(labelKey, k -> {
                Label newLabel = new Label();
                newLabel.setLabelKey(labelKey);
                newLabel.setLabelName(extractLabelNameFromKey(labelKey));
               // newLabel.setCustomer(customer);
                newLabel.setCustomerByCuid(customer);
                newLabel.setCreatedDate(LocalDateTime.now());
                newLabel.setUpdatedDate(LocalDateTime.now());
                return labelRepository.save(newLabel);
            });

            boolean defaultExists = labelTranslationRepository
                    .existsByLabel_LabelKeyAndLanguageCode(labelKey, defaultLanguageCode);

            if (!defaultExists) {
                LabelTranslation defaultTranslation = new LabelTranslation();
                defaultTranslation.setLabel(label);
                defaultTranslation.setLanguageCode(defaultLanguageCode);
                defaultTranslation.setLabelTranslated(value);
                defaultTranslation.setStatus("ACTIVE");
                defaultTranslation.setApprovedBy(approvedBy);
                defaultTranslation.setCreatedDate(LocalDateTime.now());
                defaultTranslation.setUpdatedDate(LocalDateTime.now());
                defaultTranslation.setTranslations("{}");
                labelTranslationRepository.save(defaultTranslation);
            }
        }

        // Prepare response list
        List<LabelTranslationResponseDTO> responseLanguages = new ArrayList<>();

        // Add default language response
        LabelTranslationResponseDTO defaultRes = new LabelTranslationResponseDTO();
        defaultRes.setLanguageCode(defaultLanguageCode);
        defaultRes.setTranslations(labels);
        responseLanguages.add(defaultRes);

        // Fetch all existing translations for this customer and build a lookup map
        List<LabelTranslation> existingTranslations = labelTranslationRepository.findByLabel_Customer_Cuid(customerCuid);
        Map<String, Map<String, LabelTranslation>> translationMap = new HashMap<>();
        for (LabelTranslation lt : existingTranslations) {
            translationMap
                    .computeIfAbsent(lt.getLabel().getLabelKey(), k -> new HashMap<>())
                    .put(lt.getLanguageCode(), lt);
        }


        // Handle other languages
        for (CustomerLang lang : customerLangs) {
            String langCode = lang.getLanguage().getLanguageKey();
            if (langCode.equals(defaultLanguageCode)) continue;

            Map<String, String> translationsToTranslate = new LinkedHashMap<>();
            Map<String, String> translatedMap = new LinkedHashMap<>();

            for (Map.Entry<String, String> entry : labels.entrySet()) {
                String labelKey = entry.getKey();

                if (translationMap.containsKey(labelKey) && translationMap.get(labelKey).containsKey(langCode)) {
                    // ✅ Use existing translation
                    translatedMap.put(labelKey, translationMap.get(labelKey).get(langCode).getLabelTranslated());
                } else {
                    // ❌ Not yet translated — needs ChatGPT
                    translationsToTranslate.put(labelKey, entry.getValue());
                }
            }

            if (!translationsToTranslate.isEmpty()) {
                Map<String, String> translatedResults = chatGptTranslator.translateBatch(
                        translationsToTranslate, defaultLanguageCode, langCode
                );

                for (Map.Entry<String, String> entry : translatedResults.entrySet()) {
                    String labelKey = entry.getKey();
                    String translatedValue = entry.getValue();

                    Label label = labelMap.get(labelKey);
                    if (label == null) continue;

                    LabelTranslation translation = new LabelTranslation();
                    translation.setLabel(label);
                    translation.setLanguageCode(langCode);
                    translation.setLabelTranslated(translatedValue);
                    translation.setStatus("ACTIVE");
                    translation.setApprovedBy(approvedBy);
                    translation.setCreatedDate(LocalDateTime.now());
                    translation.setUpdatedDate(LocalDateTime.now());

                    try {
                        Map<String, String> json = Map.of("translatedText", translatedValue);
                        translation.setTranslations(new ObjectMapper().writeValueAsString(json));
                    } catch (Exception e) {
                        translation.setTranslations("{}");
                        e.printStackTrace();
                    }

                    labelTranslationRepository.save(translation);
                    translatedMap.put(labelKey, translatedValue);
                }
            }

            if (!translatedMap.isEmpty()) {
                LabelTranslationResponseDTO res = new LabelTranslationResponseDTO();
                res.setLanguageCode(langCode);
                res.setTranslations(translatedMap);
                responseLanguages.add(res);
            }
        }

        // Build final response
        LabelResponseDTO response = new LabelResponseDTO();
        response.setCuid(customerCuid);
        response.setDefaultLanguageCode(defaultLanguageCode);
        response.setLanguages(responseLanguages);

        return response;
    }

    @Override
    public List<Label> getLabelsByCustomerCuid(UUID id) {
        return labelRepository.findByCustomer_Cuid(id);
    }
}
