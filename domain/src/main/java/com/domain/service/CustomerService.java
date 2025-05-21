package com.domain.service;

import com.domain.model.Customer;
public interface CustomerService
{
    Customer findByUserId(int id);
}
