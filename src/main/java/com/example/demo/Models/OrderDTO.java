package com.example.demo.Models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

@Data
public class OrderDTO {
    private Integer orderId;
    private int customerId;
    private Date orderDate;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> orderItems;

    @Autowired
    public OrderDTO(Integer orderId, int customerId, Date orderDate, String status, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }
}
