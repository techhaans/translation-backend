package com.domain.serviceImpl;

import com.domain.model.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.model.Customer;
import com.domain.repo.CustomerRepository;
import com.domain.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {

        return customerRepository.save(customer);
    }

    @Override
    public Customer findByUser(UserTable user) {
        return customerRepository.findByUser(user);
    }

}
