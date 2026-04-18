package com.example.demo.Services;

import org.springframework.stereotype.Service;
import com.example.demo.Entities.Users;
import com.example.demo.Repository.UserRepository;

import jakarta.persistence.EntityManager;

@Service
public class UserService {

    private final EntityManager _entityManager;
    private final UserRepository _userRepository;

    public UserService(EntityManager entityManager, UserRepository userRepository) {
        _entityManager = entityManager;
        _userRepository = userRepository;
    }

    public Users getUserById(int id) {
        // Placeholder method to get user by ID
        Users user = _entityManager.find(Users.class, id);

        return user;
    }

    //enables the user and updates the last login time to now
    //will modify to FirstTimeLogin
    public boolean updateUser(int id)
    {
        try
        {
            var userToBeModified = _userRepository.findUserByPrimaryId(id);

            if(userToBeModified != null)
            {
                userToBeModified.setEnabled('Y');
                userToBeModified.setLastLogin(new java.util.Date());
                _userRepository.save(userToBeModified);
            }
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("Error updating user: " + ex.getMessage());
            return false;
        }
    }

}
