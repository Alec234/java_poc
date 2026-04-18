package com.example.demo.Controllers;
import com.example.demo.Services.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

private final PaymentService _paymentService;

    public PaymentsController(PaymentService paymentService)
    {
        _paymentService = paymentService;
        System.out.println("PaymentsController initialized");
    }

    @PostMapping("/authorizePayment")
    public ResponseEntity<?> authorizePayment(PaymentDTO payment)
    {
        // Placeholder method to authorize a payment
        return ResponseEntity.ok("Payment authorized");
    }

    @PostMapping("/capture")
    public ResponseEntity<?> capturePayment(PaymentDTO payment)
    {
        // Placeholder method to capture a payment
        var res = _paymentService.capturePayment(payment);
        return res;
    }

    @PostMapping("/refund")
    public ResponseEntity<String> refundPayment(@RequestParam int paymentId, @RequestBody List<ProductDTO> productsToRefund)
    {
        // Placeholder method to refund a payment
        var res = _paymentService.refundPayment(paymentId, productsToRefund);
        return res;
    }
}
