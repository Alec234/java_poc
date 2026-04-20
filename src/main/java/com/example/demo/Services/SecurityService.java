package com.example.demo.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Users;
import com.example.demo.Models.CustomerDTO;
import com.example.demo.Models.UserDTO;
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

    public SecurityService(UserRepository userRepository)
    {
        _userRepository = userRepository;
        System.out.println("SecurityService initialized");
    }


    //Grabs all users and maps them to a DTO collection.
    //Add filtering to this method, similar to the GetOrders method, to filter by userId or fullName. Maybe add pagination as well if we have a lot of users in the database.
    //copilot messed with this method a lot, so we may want to rewrite it. It works, but it's not very clean.
    public ResponseEntity<List<UserDTO>> GetUsers()
    {
        List<Users> userList = _userRepository.findAll();


        List<UserDTO> userDTOList = new ArrayList<>();
        for(var user : userList)
        {
            CustomerDTO customerDTO = null;
            if (user.getCustomers() != null) {
                customerDTO = new CustomerDTO(
                    user.getCustomers().getCustomerId(),
                    user.getCustomers().getFullName(),
                    user.getCustomers().getEmail(),
                    user.getCustomers().getPhoneNumber(),
                    user.getCustomers().getAddress()
                );
            }

            //map users
            UserDTO userDTO = new UserDTO(
                user.getPrimaryId(),
                user.getUserId(),
                user.getFullName(),
                user.getLastLogin(),
                user.getEnabled(),
                customerDTO
            );
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
