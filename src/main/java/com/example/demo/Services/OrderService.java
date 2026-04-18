package com.example.demo.Services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.OrderItems;
import com.example.demo.Entities.Orders;
import com.example.demo.Models.OrderDTO;
import com.example.demo.Models.OrderItemDTO;
import com.example.demo.Repository.*;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    private final OrderRepository _orderRepository;
    private final OrderItemRepository _orderItemRepository;
    private final ProductRepository _productRepository;
    private final CustomerRepository _customerRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        _orderRepository = orderRepository;
        _orderItemRepository = orderItemRepository;
        _productRepository = productRepository;
        _customerRepository = customerRepository;
    }
    
    //might add in filtering by price later down the line, but for now just filtering by date, status, and customerId.
    //do we need to add pagination here?
    //********needs testing **********
    public ResponseEntity<List<OrderDTO>> GetOrders(Integer customerId, String status, String orderDate)
    {
        List<OrderDTO> orders = new ArrayList<>();

        try
        {
            // Placeholder method to get orders based on filters
            //if no filters are provided, return all orders. Otherwise, apply filters to the query and return the results
            if(customerId == null && status == null && orderDate == null)
            {
                //map from an entity to a DTO collection
                for(Orders order : _orderRepository.findAll())
                {
                    OrderDTO unfilteredOrderDTO = new OrderDTO(order.getOrderId(), order.getCustomers().getCustomerId(), order.getOrderDate(), order.getStatus(), order.getTotalAmount());
                    orders.add(unfilteredOrderDTO);

                }
                return ResponseEntity.ok(orders);
            }
            else
            {
                var Orders = _orderRepository.findByFilters(customerId, status, orderDate);
                //map from an entity to a DTO collection
                for(Orders order : Orders)
                {
                    OrderDTO unfilteredOrderDTO = new OrderDTO(order.getOrderId(), order.getCustomers().getCustomerId(), order.getOrderDate(), order.getStatus(), order.getTotalAmount());
                    orders.add(unfilteredOrderDTO);

                }
                return ResponseEntity.ok(orders);
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            //if theres an issue, return a server error and an empty list of orders
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orders);
        }

    }

    //still fairly new to java, so leaving some notes
    //itemOrderDTO requires an Orders object, Orders requires a Customers object.
    //The way JPA works, we can just pass it a reference to the customer entity 
    //and it will handle the foreign key for us. Same goes for the order reference in the OrderItems entity.
    @Transactional
    public boolean CreateOrder(OrderDTO order)
    {
        try
        {
            if (order == null || order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
                return false;
            }

            //orders takes a customers object, we just need to pass it a reference.
            Orders newOrder = new Orders();
            //its reasonable to assume that the customerId would be provided to us
            newOrder.setCustomers(_customerRepository.getReferenceById(order.getCustomerId()));
            newOrder.setOrderDate(new Date());
            newOrder.setStatus("Pending");

            //calculate and set the total amount for the order
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (OrderItemDTO item : order.getOrderItems()) {
                if (item.getPrice() == null || item.getQuantity() <= 0) {
                    return false;
                }

                totalAmount = totalAmount.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }

            newOrder.setTotalAmount(totalAmount);
            _orderRepository.save(newOrder);

            for (OrderItemDTO item : order.getOrderItems()) {
                OrderItems entity = new OrderItems();
                entity.setOrders(newOrder);                 // sets order_id FK
                entity.setProductId(item.getProductId());   // your scalar FK field
                entity.setQuantity(item.getQuantity());
                entity.setPrice(item.getPrice());

                _orderItemRepository.save(entity);
            }

            //calculate totals

            //create order and order items in one transaction, can use the entity directly here

            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }



    public boolean ProcessOrder(OrderDTO order)
    {
        BigDecimal price = BigDecimal.ZERO;
        try
        {
            //if one of the products doesnt exist, return an error message "One or more products in your order does not exist or is out of stock"
            var exists = ValidateOrderItems(order.getOrderId());

            if(!exists)
            {
                System.out.println("One or more products in your order does not exist or is out of stock");
                return false;
            }

            //select * from orderItem table where orderId = orderid
            //foreach productId in the returned resultset, total += quantity * price

            var userCart = _orderItemRepository.findByOrders_OrderId(order.getOrderId());
            for(var item: userCart)
            {
                price = price.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));

                //update the associated product entity
                var product = _productRepository.findById(item.getProductId());
                if(product.isPresent())
                {
                    var p = product.get();
                    p.setStockQuantity(p.getStockQuantity() - item.getQuantity());
                    _productRepository.save(p);
                }
            }

            //add tax, shipping, discount etc to total
/* 
            //update products, can use the entity directly here
            for(var item: userCart)
            {

            }
*/
            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    // this method will determine if the product exists and if stock is available
    public boolean ValidateOrderItems(int orderId)
    {

        try
        {
            //returns a list of order items in the customers order
            //contains the productId for each item and the quantity ordered
            var orderItems = _orderItemRepository.findByOrders_OrderId(orderId);

            for(OrderItems item : orderItems)
            {

                if(!ProductExists(item.getProductId()) || !ProductStockAvailable(item.getProductId(), item.getQuantity()))
                {
                    return false;
                }
            }
            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    public boolean ProductExists(int productId)
    {
        var product = _productRepository.findById(productId);
        if(product != null)
        {
            return true;
        }
        return false;
    }

    public boolean ProductStockAvailable(int productId, int quantity)
    {
        var product = _productRepository.findById(productId);
        if(product.isPresent())
        {
            if(product.get().getStockQuantity() >= quantity)
            {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public ResponseEntity<?> SubmitOrder(int id)
    {

        try
        {
            //TODO: Validate order exists; validate items in cart exist and are in stock; 
            // check if entry exists, dont allow multiple entries

            Orders currentOrder = _orderRepository.findById(id).orElse(null);
            
            if(currentOrder == null)
            {
                throw new Exception("currentOrder cannot be null");
            }

            //check if this order already exists and is not in the pending state
            //*possible that we need to add a check for other states in the future. Ex: 'draft' */
            if(!"Pending".equals(currentOrder.getStatus()))
            {
                return ResponseEntity.ok("Order is already submitted");
            }

            boolean validated = ValidateOrderItems(id);
            if(!validated)
            {
                System.out.println("Order validation failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order validation failed");
            }

            //2. Update the order status
            currentOrder.setStatus("Submitted");
            _orderRepository.save(currentOrder);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while submitting the order");
        }


        return ResponseEntity.ok("Order submitted successfully");

    }

    @Transactional
    public ResponseEntity<?> CancelOrder(int id)
    {
        try
        {
            //change this to return A 400 error if the order is already shipped, and a 500 error if the order is not found
            Optional<Orders> orderOpt = _orderRepository.findById(id);

            if(orderOpt.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
            }
            //unwrap the order from the optional wrapper
            Orders orderEntity = orderOpt.get();

            if("Shipped".equals(orderEntity.getStatus()))
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot cancel an order that has already been shipped");
            }

            orderEntity.setStatus("Cancelled");
            _orderRepository.save(orderEntity);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while cancelling the order");
        }

        return ResponseEntity.ok("Order cancelled successfully");
    }

    public BigDecimal CalculateOrderTotal(int orderId)
    {
        double total = 0.0;
        try
        {
            var orderItems = _orderItemRepository.findByOrders_OrderId(orderId);

            for(OrderItems item : orderItems)
            {
                total += item.getPrice().doubleValue() * item.getQuantity();
            }

            //add tax, shipping, discount etc to total

            //cast to BigDecimal and return
            return BigDecimal.valueOf(total);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return BigDecimal.valueOf(total);
        }
    }

}
