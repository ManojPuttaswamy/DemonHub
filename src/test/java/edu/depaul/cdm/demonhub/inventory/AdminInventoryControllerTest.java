package edu.depaul.cdm.demonhub.inventory;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

class AdminInventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private AdminInventoryController adminInventoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminInventoryController).build();
    }

    @Test
    void testGetAllInventory() throws Exception {
        when(inventoryService.findAllInventory()).thenReturn(Arrays.asList(
                new Inventory("Product1", 10, 100.0),
                new Inventory("Product2", 5, 50.0)
        ));

        mockMvc.perform(get("/api/admin/inventory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(inventoryService, times(1)).findAllInventory();
    }

    @Test
    void testGetInventoryById() throws Exception {
        Inventory inventory = new Inventory("Product1", 10, 100.0);
        when(inventoryService.findInventoryById(1L)).thenReturn(Optional.of(inventory));

        mockMvc.perform(get("/api/admin/inventory/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Product1"));

        verify(inventoryService, times(1)).findInventoryById(1L);
    }

    @Test
    void testAddInventory() throws Exception {
        Inventory inventory = new Inventory("Product3", 20, 200.0);
        when(inventoryService.saveInventory(any())).thenReturn(inventory);

        mockMvc.perform(post("/api/admin/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(inventory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Product3"));

        verify(inventoryService, times(1)).saveInventory(any());
    }

    @Test
    void testUpdateInventory() throws Exception {
        Inventory updatedInventory = new Inventory("UpdatedProduct", 15, 150.0);
        when(inventoryService.updateInventory(eq(1L), any())).thenReturn(Optional.of(updatedInventory));

        mockMvc.perform(put("/api/admin/inventory/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedInventory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("UpdatedProduct"));

        verify(inventoryService, times(1)).updateInventory(eq(1L), any());
    }

    @Test
    void testDeleteInventory() throws Exception {
        when(inventoryService.deleteInventory(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/admin/inventory/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(inventoryService, times(1)).deleteInventory(1L);
    }
}
