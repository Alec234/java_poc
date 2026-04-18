package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Customers;

public interface CustomerRepository extends JpaRepository<Customers, Integer> {
    
    Customers findByEmail(String email);
    
}
