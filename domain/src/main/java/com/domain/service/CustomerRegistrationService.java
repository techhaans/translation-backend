package com.domain.service;

import com.domain.dto.CustomerRegistrationRequest;
import com.domain.model.Customer;

import java.util.List;

public interface CustomerRegistrationService {
     void registerCustomer(CustomerRegistrationRequest request);

     List<Customer> getAllCustomer();
}
