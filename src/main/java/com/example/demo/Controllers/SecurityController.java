package com.example.demo.Controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Models.UserDTO;
import com.example.demo.Services.SecurityService;

public class SecurityController {

    private final SecurityService _securityService;

    public SecurityController(SecurityService securityService)
    {
        _securityService = securityService;
    }


    //get users
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> GetUsers()
    {
        var res = _securityService.GetUsers();
        return res;
    }

    

}
