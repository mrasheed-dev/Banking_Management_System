package com.banking.service;

import com.banking.entity.Customer;

public interface CustomerService {

    Customer saveCustomer(Customer customer);
    
    Customer loginCustomer(String email, String password);

}
