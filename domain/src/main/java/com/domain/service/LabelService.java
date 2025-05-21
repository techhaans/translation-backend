package com.domain.service;

import com.domain.dto.response.LabelResponseDTO;
import com.domain.model.Label;

import java.util.*;

public interface LabelService {


    LabelResponseDTO createOrUpdateLabels(UUID customerCuid, Map<String, String> labels);

  //  List<Label> getLabelsByCustomerCuid(UUID customerCuid);

    LabelResponseDTO getLabelTranslationsByCustomer(UUID customerCuid);

}
