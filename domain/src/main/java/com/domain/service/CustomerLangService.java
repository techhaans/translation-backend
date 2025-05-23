package com.domain.service;

import com.domain.dto.CustomerLanguageRequestDTO;
import com.domain.dto.response.CustomerLanguageResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerLangService {
    public List<CustomerLanguageResponseDTO > getLanguagesByCustomerId(UUID customerUid) ;

    void setCustomerLanguages(CustomerLanguageRequestDTO requestDTO);
}
