package com.example.demo.Controllers;
import com.example.demo.Services.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    //GET /products?page=0&size=25&sort=price,desc
    //GET /products?minPrice=...&maxPrice=...&q=...&inStock=true
    //GET /orders?customerId=...&status=...&from=...&to=...

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    //So here we take in various parameters for filtering, pagination, and sorting.
    //The service layer will handle the logic of applying these filters and returning the appropriate products.
    //could condense into a dto later on.
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        // Call the service layer to get products based on the filters and pagination
        List<ProductDTO> products = productService.getProducts(minPrice, maxPrice, name, inStock, page, size, sort);
        return ResponseEntity.ok(products);
    }

}
