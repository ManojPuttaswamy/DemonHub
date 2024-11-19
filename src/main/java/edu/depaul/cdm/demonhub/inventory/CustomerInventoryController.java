package edu.depaul.cdm.demonhub.inventory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customer/inventory")
public class CustomerInventoryController {

    private final InventoryService inventoryService;

    public CustomerInventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Inventory>> getAllAvailableInventory() {
        List<Inventory> inventoryList = inventoryService.findAllInventory();
        return ResponseEntity.ok(inventoryList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        return inventoryService.findInventoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
