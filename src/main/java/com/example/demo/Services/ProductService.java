package com.example.demo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Products;
import com.example.demo.Models.ProductDTO;
import com.example.demo.Repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository _productRepository;

    public ProductService(ProductRepository productRepository) {
        this._productRepository = productRepository;       
    }
    // This method can perform filtering and pagination based on the parameters.
    
    public List<ProductDTO> getProducts(Double minPrice, Double maxPrice, String name, Boolean inStock, int page, int size, String[] sort) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.max(size, 1);
        Pageable pageable = PageRequest.of(safePage, safeSize, buildSort(sort));
        Specification<Products> spec = buildSpecification(minPrice, maxPrice, name, inStock);

        List<Products> products = _productRepository.findAll(spec, pageable).getContent();

        return products.stream()
                .map(product -> new ProductDTO(
                        product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStockQuantity()))
                .collect(Collectors.toList());
    }
    //#region Helper Methods for Specifications and Sorting written by copilot
    private Specification<Products> buildSpecification(Double minPrice, Double maxPrice, String name, Boolean inStock) {
        return Specification.where(hasMinPrice(minPrice))
                .and(hasMaxPrice(maxPrice))
                .and(hasName(name))
                .and(isInStock(inStock));
    }

    private Specification<Products> hasMinPrice(Double minPrice) {
        return (root, query, criteriaBuilder) -> minPrice == null
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private Specification<Products> hasMaxPrice(Double maxPrice) {
        return (root, query, criteriaBuilder) -> maxPrice == null
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    private Specification<Products> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return null;
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("productName")),
                    "%" + name.toLowerCase() + "%");
        };
    }

    private Specification<Products> isInStock(Boolean inStock) {
        return (root, query, criteriaBuilder) -> Boolean.TRUE.equals(inStock)
                ? criteriaBuilder.greaterThan(root.get("stockQuantity"), 0)
                : null;
    }

    private Sort buildSort(String[] sortParams) {
        if (sortParams == null || sortParams.length == 0) {
            return Sort.by(Sort.Order.asc("productId"));
        }

        Sort sort = Sort.unsorted();

        for (String sortParam : sortParams) {
            if (sortParam == null || sortParam.isBlank()) {
                continue;
            }

            String[] parts = sortParam.split(",", 2);
            String property = mapSortProperty(parts[0]);
            Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1])
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            sort = sort.and(Sort.by(new Sort.Order(direction, property)));
        }

        return sort.isUnsorted() ? Sort.by(Sort.Order.asc("productId")) : sort;
    }

    private String mapSortProperty(String property) {
        if (property == null || property.isBlank() || "id".equalsIgnoreCase(property)) {
            return "productId";
        }

        if ("name".equalsIgnoreCase(property)) {
            return "productName";
        }

        if ("stock".equalsIgnoreCase(property) || "stockQuantity".equalsIgnoreCase(property)) {
            return "stockQuantity";
        }

        if ("price".equalsIgnoreCase(property) || "description".equalsIgnoreCase(property)) {
            return property;
        }

        return "productId";
    }
    //#endregion
}
