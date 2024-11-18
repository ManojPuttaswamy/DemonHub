package edu.depaul.cdm.demonhub.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/order")
@Tag(name = "Admin Orders", description = "Endpoints for managing orders as an admin")
@Slf4j
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("")
    @Operation(summary = "Get all orders", description = "Fetches all orders in the system")
    public ResponseEntity<List<OrderRequest>> getAllOrders() {
        log.info("Fetching all orders");
        List<OrderRequest> orders = adminOrderService.getAllOrders();
        log.info("Retrieved {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Fetches an order by its unique ID")
    public ResponseEntity<OrderRequest> getOrderById(@PathVariable Long orderId) {
        log.info("Fetching order with ID: {}", orderId);
        Optional<OrderRequest> order = adminOrderService.getOrderById(orderId);
        if (order.isPresent()) {
            log.info("Order with ID {} found", orderId);
            return ResponseEntity.ok(order.get());
        } else {
            log.warn("Order with ID {} not found", orderId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    @Operation(summary = "Create a new order", description = "Creates a new order")
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Request received to create a new order");
        OrderRequest createdOrder = adminOrderService.createOrder(orderRequest);
        log.info("Order created successfully with ID: {}", createdOrder.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Update an order", description = "Updates an existing order's details by its ID")
    public ResponseEntity<OrderRequest> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequest orderRequest) {
        log.info("Request received to update order with ID: {}", orderId);
        try {
            OrderRequest updatedOrder = adminOrderService.updateOrder(orderId, orderRequest);
            log.info("Order with ID {} updated successfully", orderId);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            log.error("Update failed: Order with ID {} not found", orderId, e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Delete an order", description = "Deletes an order by its ID")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        log.info("Request received to delete order with ID: {}", orderId);
        try {
            adminOrderService.deleteOrder(orderId);
            log.info("Order with ID {} deleted successfully", orderId);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (RuntimeException e) {
            log.error("Delete failed: Order with ID {} not found", orderId, e);
            return ResponseEntity.notFound().build();
        }
    }
}