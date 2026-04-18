package com.example.demo.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.OrderItems;
import com.example.demo.Entities.Orders;

public interface OrderItemRepository extends JpaRepository<OrderItems, UUID> {
    
    List<OrderItems> findByOrders(Orders orders);
    List<OrderItems> findByOrders_OrderId(int orderId);
    
}
