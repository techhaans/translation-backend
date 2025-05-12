package com.domain.service;

import com.domain.dto.CustomerRegistrationRequest;
import com.domain.dto.CustomerRegistrationResponse;
import com.domain.model.Customer;

import java.util.List;

public interface CustomerRegistrationService {
     CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest request);

     List<Customer> getAllCustomer();
}
