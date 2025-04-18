package com.dc.facade.fd;

import com.domain.dto.LabelResponseDTO;

import java.util.Map;
import java.util.UUID;

public interface LabelFacade {

        LabelResponseDTO processLabels(UUID customerCuid, Map<String, String> labels);

}
