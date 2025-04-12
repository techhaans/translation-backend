package com.domain.service;

import com.domain.dto.LabelResponseDTO;

import java.util.Map;

public interface LabelService {
    LabelResponseDTO createOrUpdateLabels(Integer customerId, Map<String, String> labels);

}
