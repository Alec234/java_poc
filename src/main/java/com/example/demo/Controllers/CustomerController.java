package com.example.demo.Controllers;
import com.example.demo.Services.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService _customerService;

    public CustomerController(CustomerService customerService) {
        _customerService = customerService;
        System.out.println("CustomerController initialized");
    }

}
