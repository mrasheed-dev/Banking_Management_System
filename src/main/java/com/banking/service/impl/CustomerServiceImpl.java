package com.banking.service.impl;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.entity.Customer;
import com.banking.repository.CustomerRepository;
import com.banking.service.CustomerService;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {

        // GENERATE ACCOUNT NUMBER

        Random random = new Random();

        long accountNumber = 1000000000L + random.nextInt(900000000);

        customer.setAccountNumber(accountNumber);

        // DEFAULT BALANCE

        customer.setBalance(0.0);

        return customerRepository.save(customer);
    }
    
    @Override
    public Customer loginCustomer(String email, String password)
    
    {
    	Customer customer = customerRepository.findByEmail(email);
    	
    	if(customer != null && customer.getPassword().equals(password))
    	{
    		return customer;
    	}
    	return null;
    }

}
