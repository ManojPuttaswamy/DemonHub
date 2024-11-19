package edu.depaul.cdm.demonhub.inventory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory Management", description = "Endpoints for managing inventory")
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/")
    @Operation(summary = "Get all inventory", description = "Fetch a list of all inventory items")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        log.info("Fetching all inventory items");
        List<Inventory> inventoryList = inventoryService.findAllInventory();
        log.info("Retrieved {} inventory items", inventoryList.size());
        return ResponseEntity.ok(inventoryList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get inventory by ID", description = "Fetch an inventory item by its unique ID")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        log.info("Fetching inventory item with ID: {}", id);
        return inventoryService.findInventoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Inventory item with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping("/")
    @Operation(summary = "Add a new inventory item", description = "Create a new inventory item")
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory) {
        log.info("Adding a new inventory item: {}", inventory.getProductName());
        Inventory savedInventory = inventoryService.saveInventory(inventory);
        log.info("Inventory item {} added successfully", savedInventory.getProductName());
        return ResponseEntity.ok(savedInventory);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an inventory item", description = "Update the details of an existing inventory item")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        log.info("Updating inventory item with ID: {}", id);
        return inventoryService.updateInventory(id, inventory)
                .map(updatedInventory -> {
                    log.info("Inventory item with ID {} updated successfully", id);
                    return ResponseEntity.ok(updatedInventory);
                })
                .orElseGet(() -> {
                    log.warn("Inventory item with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an inventory item", description = "Delete an inventory item by its unique ID")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        log.info("Deleting inventory item with ID: {}", id);
        if (inventoryService.deleteInventory(id)) {
            log.info("Inventory item with ID {} deleted successfully", id);
            return ResponseEntity.ok().build();
        } else {
            log.warn("Inventory item with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
