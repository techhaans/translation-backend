package com.dc.api.controller;

import com.dc.facade.fd.CustomerRegistrationFacade;
import com.domain.dto.CustomerRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerRegistrationController {
    @Autowired
    private CustomerRegistrationFacade customerRegistrationFacade;

    @PostMapping("/register")
    public String registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerRegistrationFacade.registerCustomer(request);
        return "Customer registered successfully";
    }
    @GetMapping
    public ResponseEntity<?> getAllCustomer() {
        return ResponseEntity.ok(customerRegistrationFacade.getAllCustomer());
    }
}
