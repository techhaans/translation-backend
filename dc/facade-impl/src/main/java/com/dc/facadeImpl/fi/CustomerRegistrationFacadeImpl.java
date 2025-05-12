package com.dc.facadeImpl.fi;

import com.dc.facade.fd.CustomerRegistrationFacade;
import com.domain.dto.CustomerRegistrationRequest;
import com.domain.dto.CustomerRegistrationResponse;
import com.domain.service.CustomerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerRegistrationFacadeImpl implements CustomerRegistrationFacade {

    @Autowired
    private CustomerRegistrationService customerRegistrationService;

    @Override
    public CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest request) {
       return customerRegistrationService.registerCustomer(request);
    }
    @Override
    public List<?> getAllCustomer() {
        return customerRegistrationService.getAllCustomer();
    }
}
