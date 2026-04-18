package com.example.demo.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.Entities.Orders;
import com.example.demo.Entities.Payments;
import com.example.demo.Models.PaymentDTO;
import com.example.demo.Models.ProductDTO;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void capturePaymentRejectsDuplicatePaymentForOrder() {
        Orders order = new Orders();
        order.setOrderId(10);
        PaymentDTO payment = new PaymentDTO(0, 10, null, "Visa", null);

        when(orderRepository.getReferenceById(10)).thenReturn(order);
        when(paymentRepository.findByOrders(order)).thenReturn(List.of(new Payments()));

        ResponseEntity<String> response = paymentService.capturePayment(payment);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Payment already exists for this order", response.getBody());
        verify(paymentRepository, never()).save(any(Payments.class));
    }

    @Test
    void capturePaymentSavesPaymentWhenOrderHasNoExistingPayment() {
        Orders order = new Orders();
        order.setOrderId(11);
        PaymentDTO payment = new PaymentDTO(0, 11, null, "Mastercard", null);

        when(orderRepository.getReferenceById(11)).thenReturn(order);
        when(paymentRepository.findByOrders(order)).thenReturn(List.of());
        when(orderService.CalculateOrderTotal(11)).thenReturn(new BigDecimal("49.99"));

        ResponseEntity<String> response = paymentService.capturePayment(payment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment captured", response.getBody());

        ArgumentCaptor<Payments> paymentCaptor = ArgumentCaptor.forClass(Payments.class);
        verify(paymentRepository).save(paymentCaptor.capture());

        Payments savedPayment = paymentCaptor.getValue();
        assertEquals("Mastercard", savedPayment.getPaymentMethod());
        assertEquals(new BigDecimal("49.99"), savedPayment.getAmount());
        assertEquals(order, savedPayment.getOrders());
        assertNotNull(savedPayment.getPaymentDate());
    }

    @Test
    void refundPaymentMarksOrderRefundedWhenFullAmountIsRefunded() {
        Orders order = new Orders();
        order.setOrderId(22);

        Payments payment = new Payments();
        payment.setOrders(order);
        payment.setAmount(new BigDecimal("20.00"));

        ProductDTO refundedProduct = new ProductDTO(1, "Mouse", "Wireless", new BigDecimal("10.00"), 2);

        when(paymentRepository.findById(7)).thenReturn(Optional.of(payment));
        when(orderRepository.findById(22)).thenReturn(Optional.of(order));

        ResponseEntity<String> response = paymentService.refundPayment(7, List.of(refundedProduct));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment refunded", response.getBody());
        assertEquals(0, payment.getAmount().compareTo(BigDecimal.ZERO));
        assertEquals("refunded", order.getStatus());
        verify(orderRepository).save(order);
        verify(paymentRepository).save(payment);
    }
}