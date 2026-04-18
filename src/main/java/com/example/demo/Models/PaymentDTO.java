package com.example.demo.Models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Data
public class PaymentDTO {
    private int paymentId;
    private int orderId;
    private String paymentDate;
    private String paymentMethod;
    private BigDecimal amount;

    @Autowired
    public PaymentDTO(int paymentId, int orderId, String paymentDate, String paymentMethod, BigDecimal amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }
}
