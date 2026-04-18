package com.example.demo.Models;


import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Data
public class UserDTO {
    private int primaryId;
    private String userId;
    private String fullName;
    private String lastLogin;
    private String enabled;
    private CustomerDTO customers;

    @Autowired
    public UserDTO(int primaryId, String userId, String fullName, String lastLogin, String enabled, CustomerDTO customers) {
        this.primaryId = primaryId;
        this.userId = userId;
        this.fullName = fullName;
        this.lastLogin = lastLogin;
        this.enabled = enabled;
        this.customers = customers;
    }



}