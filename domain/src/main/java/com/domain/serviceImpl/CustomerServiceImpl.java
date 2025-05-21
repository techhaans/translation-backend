package com.domain.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.model.Customer;
import com.domain.repo.CustomerRepository;
import com.domain.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer findByUserId(int id) {
        return customerRepository.findByUser_Id(id);
    }
}
