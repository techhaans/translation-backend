package com.dc.facadeImpl.fi;
import com.domain.dto.LabelResponseDTO;
import com.domain.service.LabelService;
import com.dc.facade.fd.LabelFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LabelFacadeImpl implements LabelFacade {

    @Autowired
    private LabelService labelService;

    @Override
    public LabelResponseDTO processLabels(UUID customerCuid, Map<String, String> labels) {
        return labelService.createOrUpdateLabels(customerCuid, labels);
    }

}
