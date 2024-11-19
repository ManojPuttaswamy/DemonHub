package edu.depaul.cdm.demonhub.inventory;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllInventory() {
        List<Inventory> mockInventory = Arrays.asList(
                new Inventory("Product1", 10, 100.0),
                new Inventory("Product2", 5, 50.0)
        );
        when(inventoryRepository.findAll()).thenReturn(mockInventory);

        List<Inventory> result = inventoryService.findAllInventory();

        assertEquals(2, result.size());
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void testFindInventoryById() {
        Inventory mockInventory = new Inventory("Product1", 10, 100.0);
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(mockInventory));

        Optional<Inventory> result = inventoryService.findInventoryById(1L);

        assertTrue(result.isPresent());
        assertEquals("Product1", result.get().getProductName());
        verify(inventoryRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveInventory() {
        Inventory newInventory = new Inventory("Product3", 20, 200.0);
        when(inventoryRepository.save(newInventory)).thenReturn(newInventory);

        Inventory result = inventoryService.saveInventory(newInventory);

        assertEquals("Product3", result.getProductName());
        verify(inventoryRepository, times(1)).save(newInventory);
    }

    @Test
    void testUpdateInventory() {
        Inventory existingInventory = new Inventory("Product1", 10, 100.0);
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(existingInventory));

        Inventory updatedDetails = new Inventory("UpdatedProduct", 15, 150.0);
        when(inventoryRepository.save(existingInventory)).thenReturn(existingInventory);

        Optional<Inventory> result = inventoryService.updateInventory(1L, updatedDetails);

        assertTrue(result.isPresent());
        assertEquals("UpdatedProduct", result.get().getProductName());
        verify(inventoryRepository, times(1)).findById(1L);
        verify(inventoryRepository, times(1)).save(existingInventory);
    }

    @Test
    void testDeleteInventory() {
        when(inventoryRepository.existsById(1L)).thenReturn(true);

        boolean result = inventoryService.deleteInventory(1L);

        assertTrue(result);
        verify(inventoryRepository, times(1)).deleteById(1L);
    }
}
