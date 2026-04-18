package com.example.demo.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.demo.Entities.Products;
import com.example.demo.Models.ProductDTO;
import com.example.demo.Repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getProductsMapsRepositoryResultsToDtos() {
        Products product = new Products();
        product.setProductId(1);
        product.setProductName("Keyboard");
        product.setDescription("Mechanical keyboard");
        product.setPrice(new BigDecimal("89.99"));
        product.setStockQuantity(12);

        when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(product)));

        List<ProductDTO> results = productService.getProducts(null, null, null, null, 0, 25, new String[] { "price,desc" });

        assertEquals(1, results.size());
        assertEquals("Keyboard", results.get(0).getProductName());
        assertEquals(new BigDecimal("89.99"), results.get(0).getPrice());
    }

    @Test
    void getProductsBuildsSortablePageRequest() {
        when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        productService.getProducts(null, null, null, null, 0, 25, new String[] { "price,desc" });

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(productRepository).findAll(any(Specification.class), pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertEquals(0, pageable.getPageNumber());
        assertEquals(25, pageable.getPageSize());
        assertNotNull(pageable.getSort().getOrderFor("price"));
        assertEquals(org.springframework.data.domain.Sort.Direction.DESC,
                pageable.getSort().getOrderFor("price").getDirection());
    }

    @Test
    void getProductsClampsNegativePageAndSize() {
        when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        productService.getProducts(null, null, null, null, -4, 0, new String[] { "id,asc" });

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(productRepository).findAll(any(Specification.class), pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertEquals(0, pageable.getPageNumber());
        assertEquals(1, pageable.getPageSize());
    }
}