package com.domain.serviceImpl;

import com.domain.dto.CustomerRegistrationRequest;
import com.domain.dto.CustomerRegistrationResponse;
import com.domain.model.Customer;
import com.domain.model.UserTable;
import com.domain.repo.CustomerRepository;
import com.domain.repo.UserRepository;
import com.domain.service.CustomerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest request) {
        // Create user
        UserTable user = new UserTable();
        user.setName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("CUSTOMER");
        userRepository.save(user);

        // Create and save customer info
        Customer customer = new Customer();
        customer.setCname(request.getName());
        customer.setCountry(request.getCountry());
        customer.setMembershipType(request.getMembershipType());
        customer.setStatus("ACTIVE");
        customer.setUser(user);
        customer.setCreatedDate(LocalDateTime.now());
        customer.setUpdatedDate(LocalDateTime.now());

        customerRepository.save(customer);

        // Return the CustomerRegistrationResponse
        return new CustomerRegistrationResponse(
                customer.getCuid(),
                user.getId(),
                user.getName(),
                user.getRole()
        );
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }
}
