package edu.depaul.cdm.demonhub.shipment;

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
@RequestMapping("/api/shipments")
@Tag(name = "Shipments", description = "Endpoints for managing shipments")
@Slf4j
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("")
    @Operation(summary = "Get all shipments", description = "Fetches all shipments in the system")
    public ResponseEntity<List<Shipment>> getAllShipments() {
        log.info("Fetching all shipments");
        List<Shipment> shipments = shipmentService.getAllShipments();
        log.info("Retrieved {} shipments", shipments.size());
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get shipment by ID", description = "Fetches a shipment by its unique ID")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable String id) {
        log.info("Fetching shipment with ID: {}", id);
        Optional<Shipment> shipment = shipmentService.getShipmentById(id);
        if (shipment.isPresent()) {
            log.info("Shipment with ID {} found", id);
            return ResponseEntity.ok(shipment.get());
        } else {
            log.warn("Shipment with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get shipments by order ID", description = "Fetches shipments linked to a specific order ID")
    public ResponseEntity<List<Shipment>> getShipmentsByOrderId(@PathVariable Long orderId) {
        log.info("Fetching shipments for order ID: {}", orderId);
        List<Shipment> shipments = shipmentService.getShipmentsByOrderId(orderId);
        log.info("Retrieved {} shipments for order ID: {}", shipments.size(), orderId);
        return ResponseEntity.ok(shipments);
    }

    @PostMapping("")
    @Operation(summary = "Create a new shipment", description = "Creates a new shipment with the provided details")
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) {
        log.info("Request received to create a new shipment");
        Shipment createdShipment = shipmentService.createShipment(shipment);
        log.info("Shipment created successfully with ID: {}", createdShipment.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShipment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a shipment", description = "Updates an existing shipment by its ID")
    public ResponseEntity<Shipment> updateShipment(@PathVariable String id, @RequestBody Shipment shipment) {
        log.info("Request received to update shipment with ID: {}", id);
        try {
            Shipment updatedShipment = shipmentService.updateShipment(id, shipment);
            log.info("Shipment with ID {} updated successfully", id);
            return ResponseEntity.ok(updatedShipment);
        } catch (RuntimeException e) {
            log.error("Update failed: Shipment with ID {} not found", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a shipment", description = "Deletes a shipment by its unique ID")
    public ResponseEntity<String> deleteShipment(@PathVariable String id) {
        log.info("Request received to delete shipment with ID: {}", id);
        try {
            shipmentService.deleteShipment(id);
            log.info("Shipment with ID {} deleted successfully", id);
            return ResponseEntity.ok("Shipment deleted successfully");
        } catch (RuntimeException e) {
            log.error("Delete failed: Shipment with ID {} not found", id, e);
            return ResponseEntity.notFound().build();
        }
    }
}