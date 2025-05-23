package com.dc.facade.fd;

import com.domain.dto.CustomerLanguageRequestDTO;
import com.domain.dto.response.CustomerLanguageResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerLangFacade {
    List<CustomerLanguageResponseDTO> fetchCustomerLanguages(UUID customerUid);

    void setCustomerLanguages(CustomerLanguageRequestDTO requestDTO);
}

