package com.domain.service;

import com.domain.dto.CustomerLanguageResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerLangService {
    public List<CustomerLanguageResponseDTO > getLanguagesByCustomerId(UUID customerUid) ;
}
