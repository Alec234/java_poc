package com.example.demo.Models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class CustomerDTO {
    private Integer customerId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;

    @Autowired
    public CustomerDTO(int customerId, String fullName, String email, String phoneNumber, String address) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
