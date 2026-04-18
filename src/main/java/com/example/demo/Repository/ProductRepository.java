package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.Entities.Products;

public interface ProductRepository extends JpaSpecificationExecutor<Products>, JpaRepository<Products, Integer> {
    
    Products findByProductName(String productName);
    Products findByProductId(int productId);
    
    List<Products> findByStockQuantityGreaterThan(int quantity);
    
}
