package com.domain.serviceImpl;

import com.domain.service.LabelService;
import com.domain.model.Customer;
import com.domain.model.Label;
import com.domain.model.LabelTranslation;
import com.domain.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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

    @Override
    public void createOrUpdateLabels(Integer customerId, String defaultLanguage, List<String> languageList, Map<String, String> labelData) {
        // Step 1: Validate Customer
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found with ID: " + customerId);
        }
        Customer customer = customerOpt.get();

        // Step 2: Fetch all existing labels for the customer
        List<Label> existingLabels = labelRepository.findByCustomer_Cid(customerId);
        Map<String, Label> existingLabelMap = new HashMap<>();
        for (Label label : existingLabels) {
            existingLabelMap.put(label.getLabelName(), label);
        }

        // Step 3: Process incoming label data
        for (Map.Entry<String, String> entry : labelData.entrySet()) {
            String labelKey = entry.getKey();
            String defaultTranslation = entry.getValue();

            Label label = existingLabelMap.get(labelKey);
            if (label == null) {
                // New Label
                LabelTranslation translation = new LabelTranslation();
                translation.setLanguage(defaultLanguage);
                translation.setLabelTranslated(defaultTranslation);
                translation.setStatus("ACTIVE");
                translation.setCreatedDate(LocalDateTime.now());
                translation.setUpdatedDate(LocalDateTime.now());
                labelTranslationRepository.save(translation);

                label = new Label();
                label.setLabelName(labelKey);
                label.setCustomer(customer);
                label.setLabelTranslation(translation);
                label.setCreatedDate(LocalDateTime.now());
                label.setUpdatedDate(LocalDateTime.now());
                labelRepository.save(label);
            } else {
                // Existing label - update translation if needed
                LabelTranslation translation = label.getLabelTranslation();
                if (!translation.getLabelTranslated().equals(defaultTranslation)) {
                    translation.setLabelTranslated(defaultTranslation);
                    translation.setUpdatedDate(LocalDateTime.now());
                    labelTranslationRepository.save(translation);
                }
            }
        }

        // Step 4: Inactivate labels that are not present in the request
        Set<String> incomingLabels = labelData.keySet();
        for (Label existing : existingLabels) {
            if (!incomingLabels.contains(existing.getLabelName())) {
                existing.setUpdatedDate(LocalDateTime.now());
                labelRepository.save(existing);
            }
        }
    }
}
