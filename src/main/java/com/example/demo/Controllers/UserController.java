package com.example.demo.Controllers;
import com.example.demo.Entities.Users;
import com.example.demo.Services.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService _userService;


    public UserController(UserService userService)
    {
        _userService = userService;
        System.out.println("UserController initialized");
    }

    @GetMapping("/user/{id}")
    public Users getUserById(int id) {
        // Placeholder method to get user by ID
        var res = _userService.getUserById(id);
        return res;
    }

    @PostMapping("/updateUser/{id}")
    public boolean updateUser(int id)
    {
        var res = _userService.updateUser(id);
        return res;
    }
/*
    @PostMapping("/deleteUser/{id}")
    public boolean deleteUser(int id)    {
        var res = _userService.deleteUser(id);
        return res;
    }

    @PostMapping("/createUser")
    public boolean createUser(@RequestBody Users user)    {
        var res = _userService.createUser(user);
        return res;
    }
        */
}
