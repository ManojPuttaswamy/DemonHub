package edu.depaul.cdm.demonhub.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer/order")
@Tag(name = "Customer Orders", description = "Endpoints for managing customer orders")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get all orders by user", description = "Fetches all orders for a specific user")
    public ResponseEntity<List<OrderRequest>> getOrdersByUser(@PathVariable String userId) {
        return ResponseEntity.ok(customerOrderService.getOrdersByUserId(userId));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get order by ID", description = "Fetches an order by its unique ID")
    public ResponseEntity<OrderRequest> getOrderById(@PathVariable Long orderId) {
        Optional<OrderRequest> order = customerOrderService.getOrderById(orderId);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Create a new order", description = "Creates a new order for a specific user")
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest orderRequest, @PathVariable String userId) {
        OrderRequest createdOrder = customerOrderService.createOrder(orderRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Cancel an order", description = "Cancels an existing order by its ID")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        try {
            customerOrderService.cancelOrder(orderId);
            return ResponseEntity.ok("Order cancelled successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}