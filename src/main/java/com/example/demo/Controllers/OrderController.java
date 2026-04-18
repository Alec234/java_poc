package com.example.demo.Controllers;
import com.example.demo.Services.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService _orderService;

    public OrderController(OrderService orderService)
    {
        _orderService = orderService;
        System.out.println("OrderController initialized");
    }

    @PostMapping("/createOrder")
    public boolean createOrder(@RequestBody OrderDTO order)
    {
        // Placeholder method to create an order
        var res = _orderService.CreateOrder(order);
        return res;
    }

    @PostMapping("/{id}/submitOrder")
    public ResponseEntity<?> submitOrder(@PathVariable int id)
    {
        var res = _orderService.SubmitOrder(id);
        return res;
    }

    //POST /orders/{id}/cancel
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable int id)
    {
        var res = _orderService.CancelOrder(id);
        return res;
    }

    
    //GET /orders?customerId=...&status=...&orderDate
    @GetMapping("")
    public ResponseEntity<List<OrderDTO>> getOrders(
        @RequestParam(required = false) Integer customerId,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String orderDate)
    {
        var res = _orderService.GetOrders(customerId, status, orderDate);
        return res;
    }

}
