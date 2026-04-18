package com.example.demo.Models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemDTO {
    private UUID orderItemId;
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal price;

    @Autowired
    public OrderItemDTO(UUID orderItemId, int orderId, int productId, int quantity, BigDecimal price) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
