package com.dc.api.controller;

import com.dc.facade.fd.CustomerRegistrationFacade;
import com.domain.dto.CustomerRegistrationRequest;
import com.domain.dto.CustomerRegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerRegistrationController {
    @Autowired
    private CustomerRegistrationFacade customerRegistrationFacade;

    @PostMapping("/register")
    public ResponseEntity<CustomerRegistrationResponse> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        CustomerRegistrationResponse response = customerRegistrationFacade.registerCustomer(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomer() {
        return ResponseEntity.ok(customerRegistrationFacade.getAllCustomer());
    }
}
