package edu.depaul.cdm.temp.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> findAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> findInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Optional<Inventory> updateInventory(Long id, Inventory inventoryDetails) {
        return inventoryRepository.findById(id)
            .map(existingInventory -> {
                existingInventory.setProductName(inventoryDetails.getProductName());
                existingInventory.setQuantity(inventoryDetails.getQuantity());
                existingInventory.setPrice(inventoryDetails.getPrice());
                return inventoryRepository.save(existingInventory);
            });
    }

    public boolean deleteInventory(Long id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
