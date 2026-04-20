package com.example.demo.Models;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {
    private Integer primaryId;
    private String userId;
    private String fullName;
    private Date lastLogin;
    private Character enabled;
    private CustomerDTO customers;

    public UserDTO(Integer primaryId, String userId, String fullName, Date lastLogin, Character enabled, CustomerDTO customers) {
        this.primaryId = primaryId;
        this.userId = userId;
        this.fullName = fullName;
        this.lastLogin = lastLogin;
        this.enabled = enabled;
        this.customers = customers;
    }



}