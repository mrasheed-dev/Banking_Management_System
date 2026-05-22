package com.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.banking.entity.Customer;
import com.banking.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private CustomerRepository customerRepository;

    // LOAD ADMIN LOGIN PAGE

    @GetMapping("/admin")
    public String loadAdminLogin() {

        return "admin-login";
    }

    // ADMIN LOGIN

    @PostMapping("/adminLogin")
    public String adminLogin(@RequestParam String email,
                             @RequestParam String password,
                             Model model,
                             HttpSession session) {

        if(email.equals("admin@gmail.com")
                && password.equals("admin123")) {

            session.setAttribute("admin", "ADMIN");

            List<Customer> customerList =
                    customerRepository.findAll();

            model.addAttribute("customers",
                    customerList);

            return "admin-dashboard";
        }

        model.addAttribute("errorMessage",
                "Invalid Admin Credentials");

        return "admin-login";
    }

    // VIEW ALL CUSTOMERS

    @GetMapping("/viewCustomers")
    public String viewCustomers(Model model,
                                HttpSession session) {

        if(session.getAttribute("admin") == null) {

            return "redirect:/admin";
        }

        List<Customer> customerList =
                customerRepository.findAll();

        model.addAttribute("customers",
                customerList);

        return "admin-dashboard";
    }

    // DELETE CUSTOMER

    @GetMapping("/deleteCustomer")
    public String deleteCustomer(@RequestParam Long id,
                                 HttpSession session) {

        if(session.getAttribute("admin") == null) {

            return "redirect:/admin";
        }

        customerRepository.deleteById(id);

        return "redirect:/viewCustomers";
    }

    // ADMIN LOGOUT

    @GetMapping("/adminLogout")
    public String adminLogout(HttpSession session) {

        session.invalidate();

        return "redirect:/admin";
    }

}