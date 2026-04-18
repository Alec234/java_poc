package com.example.demo.Models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private int productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private int stockQuantity;

    @Autowired
    public ProductDTO(int productId, String productName, String description, BigDecimal price, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
