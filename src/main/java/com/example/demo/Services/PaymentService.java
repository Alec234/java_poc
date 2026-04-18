package com.example.demo.Services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.example.demo.Entities.Payments;
import com.example.demo.Models.PaymentDTO;
import com.example.demo.Models.ProductDTO;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {


    private final OrderService _orderService;
    private final OrderRepository _orders;
    private final PaymentRepository _paymentRepository;

    public PaymentService(OrderService orderService, OrderRepository orders, PaymentRepository paymentRepository) {
        _orderService = orderService;
        _orders = orders;
        _paymentRepository = paymentRepository;
    }

    //assume payment method and orderId are the only populated fields
    @Transactional
    public ResponseEntity<String> capturePayment(PaymentDTO payment)
    {
        try
        {
            //We need some level of idempotency here, so we will check if a payment already exists for this order
            // before creating a new one
            var existingPayment = _paymentRepository.findByOrders(_orders.getReferenceById(payment.getOrderId()));
            if(!existingPayment.isEmpty())
            {
                return new ResponseEntity<>("Payment already exists for this order", HttpStatus.BAD_REQUEST);
            }

            Payments paymentEntity = new Payments();
            paymentEntity.setPaymentDate(new Date());
            paymentEntity.setAmount(_orderService.CalculateOrderTotal(payment.getOrderId()));
            //Java limitation - DTO has orderId, entity requires reference
            paymentEntity.setOrders(_orders.getReferenceById(payment.getOrderId()));
            paymentEntity.setPaymentMethod(payment.getPaymentMethod());
            //paymentMethod/orderId/paymentDate/amount are all populated at this point, can save to DB or call external gateway here
            
            // Placeholder method to capture a payment
            _paymentRepository.save(paymentEntity);
        }
        catch(Exception ex)
        {
            System.out.println("Error capturing payment: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>("Payment captured", HttpStatus.OK);
    }


    public ResponseEntity<String> refundPayment(int paymentId, List<ProductDTO> productsToRefund)
    {
        try
        {
            // Placeholder method to refund a payment
            // In a real implementation, you would look up the payment by ID, verify the amount, and process the refund through your payment gateway
            BigDecimal totalToRefund = BigDecimal.ZERO;

            for(ProductDTO product : productsToRefund)
            {
                totalToRefund = totalToRefund.add(product.getPrice().multiply(BigDecimal.valueOf(product.getStockQuantity())));
            }
            

                Payments paymentEntity = _paymentRepository.findById(paymentId).orElseThrow(() -> new Exception("Payment not found"));
                if(paymentEntity.getAmount().compareTo(totalToRefund) < 0)
                {
                    throw new Exception("Refund amount exceeds original payment");
                }
                else
                {
                    // Process refund through payment gateway here, then update payment record if successful
                    //for now we will simply update the payment record to reflect the refund;
                    paymentEntity.setAmount(paymentEntity.getAmount().subtract(totalToRefund));
                    //we should set the order status to 'refunded' if the entire amount is refunded,
                    // or 'partially refunded' if only part of the amount is refunded.
                    _orders.findById(paymentEntity.getOrders().getOrderId()).ifPresent(order -> {
                        //if no balance, mark as fully refunded, otherwise mark as partially refunded
                        if(paymentEntity.getAmount().compareTo(BigDecimal.ZERO) == 0)
                        {
                            order.setStatus("refunded");
                        }
                        else
                        {
                            order.setStatus("partially refunded");
                        }
                        _orders.save(order);
                    });

                    _paymentRepository.save(paymentEntity);
                }

            return new ResponseEntity<>("Payment refunded", HttpStatus.OK);

        }
        catch(Exception ex)
        {
            System.out.println("Error refunding payment: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
