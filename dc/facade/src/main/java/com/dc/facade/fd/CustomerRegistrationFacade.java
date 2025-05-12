package com.dc.facade.fd;

import com.domain.dto.CustomerRegistrationRequest;
import com.domain.dto.CustomerRegistrationResponse;

import java.util.List;

public interface CustomerRegistrationFacade {
    CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest request);
    List<?> getAllCustomer() ;
}
