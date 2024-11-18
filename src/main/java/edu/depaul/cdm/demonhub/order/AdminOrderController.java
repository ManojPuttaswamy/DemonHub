package edu.depaul.cdm.demonhub.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/order")
@Tag(name = "Admin Orders", description = "Endpoints for managing orders as an admin")
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("")
    @Operation(summary = "Get all orders", description = "Fetches all orders in the system")
    public ResponseEntity<List<OrderRequest>> getAllOrders() {
        return ResponseEntity.ok(adminOrderService.getAllOrders());
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Fetches an order by its unique ID")
    public ResponseEntity<OrderRequest> getOrderById(@PathVariable Long orderId) {
        Optional<OrderRequest> order = adminOrderService.getOrderById(orderId);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @Operation(summary = "Create a new order", description = "Creates a new order")
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderRequest createdOrder = adminOrderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Update an order", description = "Updates an existing order's details by its ID")
    public ResponseEntity<OrderRequest> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequest orderRequest) {
        try {
            OrderRequest updatedOrder = adminOrderService.updateOrder(orderId, orderRequest);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Delete an order", description = "Deletes an order by its ID")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        try {
            adminOrderService.deleteOrder(orderId);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}