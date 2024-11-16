package edu.depaul.cdm.demonhub.inventory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        List<Inventory> inventoryList = inventoryService.findAllInventory();
        return ResponseEntity.ok(inventoryList); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        return inventoryService.findInventoryById(id)
                .map(ResponseEntity::ok) 
                .orElseGet(() -> ResponseEntity.notFound().build()); 
    }

    @PostMapping("/")
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryService.saveInventory(inventory);
        return ResponseEntity.ok(savedInventory); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        return inventoryService.updateInventory(id, inventory)
                .map(ResponseEntity::ok) 
                .orElseGet(() -> ResponseEntity.notFound().build()); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        if (inventoryService.deleteInventory(id)) {
            return ResponseEntity.ok().build(); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
}
