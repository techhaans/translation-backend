package com.dc.facadeImpl.fi;
import com.dc.facade.fd.CustomerLangFacade;
import com.domain.dto.CustomerLanguageResponseDTO;
import com.domain.service.CustomerLangService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CustomerLangFacadeImpl implements CustomerLangFacade {

    private final CustomerLangService customerLangService;

    public CustomerLangFacadeImpl(CustomerLangService customerLangService) {
        this.customerLangService = customerLangService;
    }

    @Override
    public List<CustomerLanguageResponseDTO> fetchCustomerLanguages(UUID customerUid) {
        return customerLangService.getLanguagesByCustomerId(customerUid);
    }
}

