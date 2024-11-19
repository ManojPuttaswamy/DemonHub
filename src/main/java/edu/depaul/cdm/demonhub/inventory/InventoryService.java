package edu.depaul.cdm.demonhub.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> findAllInventory() {
        log.info("Fetching all inventory items");
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> findInventoryById(Long id) {
        log.info("Fetching inventory item with ID: {}", id);
        return inventoryRepository.findById(id);
    }

    public Inventory saveInventory(Inventory inventory) {
        log.info("Saving inventory item: {}", inventory.getProductName());
        return inventoryRepository.save(inventory);
    }

    public Optional<Inventory> updateInventory(Long id, Inventory inventoryDetails) {
        return inventoryRepository.findById(id).map(existingInventory -> {
            log.info("Updating inventory item with ID: {}", id);
            existingInventory.setProductName(inventoryDetails.getProductName());
            existingInventory.setQuantity(inventoryDetails.getQuantity());
            existingInventory.setPrice(inventoryDetails.getPrice());
            return inventoryRepository.save(existingInventory);
        });
    }

    public boolean deleteInventory(Long id) {
        if (inventoryRepository.existsById(id)) {
            log.info("Deleting inventory item with ID: {}", id);
            inventoryRepository.deleteById(id);
            return true;
        }
        log.warn("Inventory item with ID {} not found", id);
        return false;
    }
}
