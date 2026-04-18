package com.example.demo.Services;
import com.example.demo.Models.CustomerDTO;
import com.example.demo.Repository.*;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.Customers;

@Service
public class CustomerService {

    private final CustomerRepository _customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        _customerRepository = customerRepository;
        System.out.println("CustomerService initialized");
    }

    public boolean createCustomer(CustomerDTO newCustomer) {
        try
        {

            if(newCustomer.getCustomerId() != null)
            {
                System.err.println("Customer ID should not be provided for new customers.");
                throw new IllegalArgumentException("Customer ID should not be provided for new customers.");
            }
            //map newCustomer to CustomerEntity
            Customers customerEntity = new Customers();
            customerEntity.setFullName(newCustomer.getFullName());
            customerEntity.setEmail(newCustomer.getEmail());
            customerEntity.setPhoneNumber(newCustomer.getPhoneNumber());
            customerEntity.setAddress(newCustomer.getAddress());

            _customerRepository.save(customerEntity);
            return true;
        } 
        catch (Exception e)
        {
            System.err.println("Error creating customer: " + e.getMessage());
            return false;
        }
    }

}

