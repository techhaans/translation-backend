package com.domain.serviceImpl;

import com.domain.dto.CustomerLanguageResponseDTO;
import com.domain.model.CustomerLang;
import com.domain.repo.CustomerLangRepository;
import com.domain.service.CustomerLangService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerLangServiceImpl implements CustomerLangService {

    private final CustomerLangRepository customerLangRepository;

    public CustomerLangServiceImpl(CustomerLangRepository customerLangRepository) {
        this.customerLangRepository = customerLangRepository;
    }

    @Override
    public List<CustomerLanguageResponseDTO> getLanguagesByCustomerId(UUID customerUid) {
        List<CustomerLang> customerLangList = customerLangRepository.findByCustomer_Cuid(customerUid);

        return customerLangList.stream()
                .map(cl -> new CustomerLanguageResponseDTO(
                        cl.getLanguage().getLanguageKey(),
                        cl.getLanguage().getLname(),
                        Boolean.TRUE.equals(cl.getDefault())
                ))
                .collect(Collectors.toList());
    }
}
