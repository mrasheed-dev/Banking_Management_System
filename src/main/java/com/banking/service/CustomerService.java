package com.banking.service;

import com.banking.entity.Customer;

public interface CustomerService {

    Customer saveCustomer(Customer customer);
    
    Customer loginCustomer(String email, String password);
    
    void depositAmount(Long customerId, Double amount);

    boolean withdrawAmount(Long customerId, Double amount);

    boolean transferAmount(Long senderId,
                           Long receiverAccountNumber,
                           Double amount);

}
