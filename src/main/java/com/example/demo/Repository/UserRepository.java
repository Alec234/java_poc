package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Users;


public interface UserRepository extends JpaRepository<Users, Integer> {


    Users findUserByPrimaryId(int id);

    java.util.List<Users> findByCustomers_CustomerId(Integer customerId);
}
 