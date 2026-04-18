package com.example.demo.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.Entities.Customers;
import com.example.demo.Entities.OrderItems;
import com.example.demo.Entities.Orders;
import com.example.demo.Entities.Products;
import com.example.demo.Models.OrderDTO;
import com.example.demo.Models.OrderItemDTO;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.OrderItemRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrderReturnsFalseForMissingItems() {
        OrderDTO order = new OrderDTO(null, 4, null, null, null);
        order.setOrderItems(List.of());

        boolean created = orderService.CreateOrder(order);

        assertFalse(created);
        verify(orderRepository, never()).save(any(Orders.class));
    }

    @Test
    void createOrderSavesOrderAndOrderItems() {
        Customers customer = new Customers();
        customer.setCustomerId(4);

        OrderDTO order = new OrderDTO(null, 4, null, null, null);
        order.setOrderItems(List.of(new OrderItemDTO(null, 0, 99, 2, new BigDecimal("15.00"))));

        when(customerRepository.getReferenceById(4)).thenReturn(customer);

        boolean created = orderService.CreateOrder(order);

        assertTrue(created);
        verify(orderRepository).save(any(Orders.class));
        verify(orderItemRepository, times(1)).save(any(OrderItems.class));
    }

    @Test
    void submitOrderReturnsOkWhenOrderAlreadySubmitted() {
        Orders order = new Orders();
        order.setOrderId(12);
        order.setStatus("Submitted");

        when(orderRepository.findById(12)).thenReturn(Optional.of(order));

        ResponseEntity<?> response = orderService.SubmitOrder(12);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order is already submitted", response.getBody());
        verify(orderRepository, never()).save(any(Orders.class));
    }

    @Test
    void submitOrderReturnsBadRequestWhenValidationFails() {
        Orders order = new Orders();
        order.setOrderId(13);
        order.setStatus("Pending");

        OrderItems orderItem = new OrderItems();
        orderItem.setProductId(50);
        orderItem.setQuantity(3);
        orderItem.setPrice(new BigDecimal("5.00"));

        when(orderRepository.findById(13)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrders_OrderId(13)).thenReturn(List.of(orderItem));

        Products product = new Products();
        product.setProductId(50);
        product.setStockQuantity(1);
        when(productRepository.findById(50)).thenReturn(Optional.of(product));

        ResponseEntity<?> response = orderService.SubmitOrder(13);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Order validation failed", response.getBody());
    }

    @Test
    void calculateOrderTotalSumsOrderItems() {
        OrderItems first = new OrderItems();
        first.setPrice(new BigDecimal("10.00"));
        first.setQuantity(2);

        OrderItems second = new OrderItems();
        second.setPrice(new BigDecimal("4.50"));
        second.setQuantity(1);

        when(orderItemRepository.findByOrders_OrderId(20)).thenReturn(List.of(first, second));

        BigDecimal total = orderService.CalculateOrderTotal(20);

        assertEquals(BigDecimal.valueOf(24.5), total);
    }
}