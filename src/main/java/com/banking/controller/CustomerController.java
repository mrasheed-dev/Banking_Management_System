package com.banking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.banking.entity.Customer;
import com.banking.service.CustomerService;

import jakarta.validation.Valid;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // LOAD REGISTER PAGE

    @GetMapping("/register")
    public String loadRegisterPage(Model model) {

        model.addAttribute("customer", new Customer());

        return "register";
    }

    // SAVE CUSTOMER

    @PostMapping("/saveCustomer")
    public String saveCustomer(@Valid @ModelAttribute Customer customer,
                               BindingResult result,
                               Model model) {

        if(result.hasErrors()) {
            return "register";
        }

        customerService.saveCustomer(customer);

        model.addAttribute("successMessage",
                "Registration Successful");
        
        model.addAttribute("customer", new Customer());

        return "register";
    }

}
