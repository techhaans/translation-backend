package com.dc.facade.fd;

import com.domain.dto.CustomerLanguageResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerLangFacade {
    List<CustomerLanguageResponseDTO> fetchCustomerLanguages(UUID customerUid);
}

