package com.example.demo.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entities.Customers;
import com.example.demo.Entities.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    
    List<Orders> findByCustomers(Customers customers);
    
    List<Orders> findByStatus(String status);

    @Query("SELECT o FROM Orders o WHERE " +
    "(:customerId IS NULL OR o.customers.customerId = :customerId) AND " +
    "(:status IS NULL OR o.status = :status) AND " +
    "(:orderDate IS NULL OR o.orderDate >= :orderDate)")
    List<Orders> findByFilters(
    @Param("customerId") Integer customerId,
    @Param("status") String status,
    @Param("orderDate") String orderDate
    );
    List<Orders> FindByFilters(Integer customerId, String status, Date orderDate);
    
}
