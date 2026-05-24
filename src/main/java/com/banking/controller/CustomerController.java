package com.banking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.banking.entity.Customer;
import com.banking.service.CustomerService;

import jakarta.servlet.http.HttpSession;
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
    
    
    
    //LOAD LOGIN PAGE
    @GetMapping("/login")
    public String loadLoginPage()
    {
    	return "login";
    }
    
    
    
    
    //CUSTOMER LOGIN
    @PostMapping("/customerLogin")
    public String customerLogin(@RequestParam String email,
    							@RequestParam String password,
    							Model model,
    							HttpSession session)
    {
    	Customer customer = customerService.loginCustomer(email, password);
    	
    	if(customer != null)
    	{
    		session.setAttribute("loggedInCustomer", customer);
    		model.addAttribute("customer",customer);
    		
    		return "dashboard";
    	}
    	
    	model.addAttribute("errorMessage","Invalid Email or Password");
    	
    	return "Login";
    }
    
    
    
    //LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
    	session.invalidate();
    	
    	return "redirect/login";
    }
    
    
    
    //Deposite Page
    @GetMapping("/deposit")
    public String loadDepositPage() {

        return "deposit";
    }
    
    
    
    //Deposite Amount
    @PostMapping("/depositAmount")
    public String depositAmount(@RequestParam Double amount,
                                HttpSession session,
                                Model model) {

        Customer customer =
                (Customer) session.getAttribute("loggedInCustomer");

        customerService.depositAmount(customer.getId(), amount);

        Customer updatedCustomer =
                customerService.loginCustomer(
                        customer.getEmail(),
                        customer.getPassword()
                );

        session.setAttribute("loggedInCustomer",
                updatedCustomer);

        model.addAttribute("customer",
                updatedCustomer);

        model.addAttribute("successMessage",
                "Amount Deposited Successfully");

        return "dashboard";
    }
    
    
    //withdraw Page
    @GetMapping("/withdraw")
    public String loadWithdrawPage() {

        return "withdraw";
    }
    
    
    //withdraw Amount
    @PostMapping("/withdrawAmount")
    public String withdrawAmount(@RequestParam Double amount,
                                 HttpSession session,
                                 Model model) {

        Customer customer =
                (Customer) session.getAttribute("loggedInCustomer");

        boolean status =
                customerService.withdrawAmount(
                        customer.getId(),
                        amount
                );

        Customer updatedCustomer =
                customerService.loginCustomer(
                        customer.getEmail(),
                        customer.getPassword()
                );

        session.setAttribute("loggedInCustomer",
                updatedCustomer);

        model.addAttribute("customer",
                updatedCustomer);

        if(status) {

            model.addAttribute("successMessage",
                    "Amount Withdrawn Successfully");

        } else {

            model.addAttribute("errorMessage",
                    "Insufficient Balance");
        }

        return "dashboard";
    }
    
    
    
    //transfer Page
    @GetMapping("/transfer")
    public String loadTransferPage() {

        return "transfer";
    }
    
    
    
    //transfer Amount
    @PostMapping("/transferAmount")
    public String transferAmount(@RequestParam Long accountNumber,
                                 @RequestParam Double amount,
                                 HttpSession session,
                                 Model model) {

        Customer customer =
                (Customer) session.getAttribute("loggedInCustomer");

        boolean status =
                customerService.transferAmount(
                        customer.getId(),
                        accountNumber,
                        amount
                );

        Customer updatedCustomer =
                customerService.loginCustomer(
                        customer.getEmail(),
                        customer.getPassword()
                );

        session.setAttribute("loggedInCustomer",
                updatedCustomer);

        model.addAttribute("customer",
                updatedCustomer);

        if(status) {

            model.addAttribute("successMessage",
                    "Amount Transferred Successfully");

        } else {

            model.addAttribute("errorMessage",
                    "Transfer Failed");
        }

        return "dashboard";
    }
    

}
