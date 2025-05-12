package com.domain.service;

import com.domain.model.Customer;
import com.domain.model.UserTable;

public interface CustomerService
{
    public Customer addCustomer(Customer customer);

    Customer findByUser(UserTable user);
}
