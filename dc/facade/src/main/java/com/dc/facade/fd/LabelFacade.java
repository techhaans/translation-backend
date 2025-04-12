package com.dc.facade.fd;

import com.domain.dto.LabelResponseDTO;

import java.util.Map;

public interface LabelFacade {

        LabelResponseDTO processLabels(Integer customerId, Map<String, String> labels);

}
