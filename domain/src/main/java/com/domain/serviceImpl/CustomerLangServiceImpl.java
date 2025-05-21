package com.domain.serviceImpl;

import com.domain.dto.CustomerLanguageRequestDTO;
import com.domain.dto.response.CustomerLanguageResponseDTO;
import com.domain.model.Customer;
import com.domain.model.CustomerLang;
import com.domain.model.Language;
import com.domain.repo.CustomerLangRepository;
import com.domain.repo.CustomerRepository;
import com.domain.repo.LanguageRepository;
import com.domain.service.CustomerLangService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerLangServiceImpl implements CustomerLangService {

    private final CustomerLangRepository customerLangRepository;
    private final LanguageRepository languageRepository;
    private final CustomerRepository customerRepository;
    @Override
    public List<CustomerLanguageResponseDTO> getLanguagesByCustomerId(UUID customerUid) {
        List<CustomerLang> customerLangList = customerLangRepository.findByCustomer_Cuid(customerUid);

        return customerLangList.stream()
                .map(cl -> new CustomerLanguageResponseDTO(
                        cl.getLanguage().getLanguageKey(),
                        cl.getLanguage().getLanguageName(),
                        Boolean.TRUE.equals(cl.getIsDefault())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void setCustomerLanguages(CustomerLanguageRequestDTO requestDTO) {
        UUID customerUid = requestDTO.getCustomerUid();
        Customer customer = customerRepository.findByCuid(customerUid)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Delete old language mappings
        customerLangRepository.deleteByCustomer_Cuid(customerUid);

        // Create new mappings
        List<CustomerLang> customerLangs = requestDTO.getLanguages().stream()
                .map(entry -> {
                    Language language = languageRepository.findByLanguageKey(entry.getLanguageCode())
                            .orElseThrow(() -> new RuntimeException("Language not found: " + entry.getLanguageCode()));

                    CustomerLang cl = new CustomerLang();
                    cl.setCustomer(customer);
                    cl.setLanguage(language);
                    cl.setIsDefault(entry.getIsDefault());
                    return cl;
                })
                .toList();

        customerLangRepository.saveAll(customerLangs);
    }
}
