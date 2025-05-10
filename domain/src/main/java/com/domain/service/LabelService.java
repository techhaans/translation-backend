package com.domain.service;

import com.domain.dto.LabelResponseDTO;

import java.util.*;

public interface LabelService {


    LabelResponseDTO createOrUpdateLabels(UUID customerCuid, Map<String, String> labels);

}
