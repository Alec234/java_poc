package com.example.demo.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Models.UserDTO;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.UserRepository;

/*
//TODO: Create the security database with users, permissions, and roles that will contain these permissions.
    private int primaryId;
    private String userId;
    private String fullName;
    private String lastLogin;
    private String enabled;
    private String customer_id;

 */

@Service
public class SecurityService {

    private final UserRepository _userRepository;
    private final CustomerRepository _customerRepository;

    public SecurityService(UserRepository userRepository, CustomerRepository customerRepository)
    {
        _userRepository = userRepository;
        _customerRepository = customerRepository;
        System.out.println("SecurityService initialized");
    }


    //Grabs all users and maps them to a DTO collection.
    //Add filtering to this method, similar to the GetOrders method, to filter by userId or fullName. Maybe add pagination as well if we have a lot of users in the database.
    public ResponseEntity<List<UserDTO>> GetUsers(int customer_id)
    {
        var userList = _userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(var user : userList)
        {
            UserDTO userDTO = new UserDTO(user.getPrimaryId(), user.getUserId(), user.getFullName(), user.getLastLogin(), user.getEnabled(), user.getCustomers(_customerRepository.findById(customer_id).orElse(null)));
            userDTOList.add(userDTO);
        }

        // Placeholder method to get users
        return ResponseEntity.ok(userDTOList);
    }


    //check permissions


    //generate JWTs

    //login

    //get user roles

}
