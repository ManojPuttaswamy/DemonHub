package edu.depaul.cdm.demonhub.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer/order")
@Tag(name = "Customer Orders", description = "Endpoints for managing customer orders")
@Slf4j
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get all orders by user", description = "Fetches all orders for a specific user")
    public ResponseEntity<List<OrderRequest>> getOrdersByUser(@PathVariable String userId) {
        log.info("Fetching orders for user with ID: {}", userId);
        List<OrderRequest> orders = customerOrderService.getOrdersByUserId(userId);
        log.info("Retrieved {} orders for user with ID: {}", orders.size(), userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get order by ID", description = "Fetches an order by its unique ID")
    public ResponseEntity<OrderRequest> getOrderById(@PathVariable Long orderId) {
        log.info("Fetching order with ID: {}", orderId);
        Optional<OrderRequest> order = customerOrderService.getOrderById(orderId);
        if (order.isPresent()) {
            log.info("Order with ID {} found", orderId);
            return ResponseEntity.ok(order.get());
        } else {
            log.warn("Order with ID {} not found", orderId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Create a new order", description = "Creates a new order for a specific user")
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest orderRequest, @PathVariable String userId) {
        log.info("Request received to create a new order for user with ID: {}", userId);
        OrderRequest createdOrder = customerOrderService.createOrder(orderRequest, userId);
        log.info("Order created successfully with ID: {} for user with ID: {}", createdOrder.getId(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Cancel an order", description = "Cancels an existing order by its ID")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        log.info("Request received to cancel order with ID: {}", orderId);
        try {
            customerOrderService.cancelOrder(orderId);
            log.info("Order with ID {} cancelled successfully", orderId);
            return ResponseEntity.ok("Order cancelled successfully");
        } catch (RuntimeException e) {
            log.error("Cancel order failed: Order with ID {} not found", orderId, e);
            return ResponseEntity.notFound().build();
        }
    }
}