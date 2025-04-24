package com.dc.facade.fd;

import com.domain.dto.CustomerRegistrationRequest;

import java.util.List;

public interface CustomerRegistrationFacade {
    void registerCustomer(CustomerRegistrationRequest request);
    List<?> getAllCustomer() ;
}
