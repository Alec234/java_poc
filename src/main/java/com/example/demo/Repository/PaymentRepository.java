package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Orders;
import com.example.demo.Entities.Payments;

public interface PaymentRepository extends JpaRepository<Payments, Integer> {
    
    List<Payments> findByOrders(Orders orders);
    
    List<Payments> findByPaymentMethod(String paymentMethod);
    
}
