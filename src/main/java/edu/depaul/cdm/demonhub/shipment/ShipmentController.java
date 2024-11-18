package edu.depaul.cdm.demonhub.shipment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shipments")
@Tag(name = "Shipments", description = "Endpoints for managing shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("")
    @Operation(summary = "Get all shipments", description = "Fetches all shipments in the system")
    public ResponseEntity<List<Shipment>> getAllShipments() {
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get shipment by ID", description = "Fetches a shipment by its unique ID")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable String id) {
        Optional<Shipment> shipment = shipmentService.getShipmentById(id);
        return shipment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get shipments by order ID", description = "Fetches shipments linked to a specific order ID")
    public ResponseEntity<List<Shipment>> getShipmentsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(shipmentService.getShipmentsByOrderId(orderId));
    }

    @PostMapping("")
    @Operation(summary = "Create a new shipment", description = "Creates a new shipment with the provided details")
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) {
        Shipment createdShipment = shipmentService.createShipment(shipment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShipment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a shipment", description = "Updates an existing shipment by its ID")
    public ResponseEntity<Shipment> updateShipment(@PathVariable String id, @RequestBody Shipment shipment) {
        try {
            Shipment updatedShipment = shipmentService.updateShipment(id, shipment);
            return ResponseEntity.ok(updatedShipment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a shipment", description = "Deletes a shipment by its unique ID")
    public ResponseEntity<String> deleteShipment(@PathVariable String id) {
        try {
            shipmentService.deleteShipment(id);
            return ResponseEntity.ok("Shipment deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}