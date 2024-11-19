package edu.depaul.cdm.demonhub.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerInventoryController.class)
public class CustomerInventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Test
    public void testGetAllAvailableInventory() throws Exception {
        Inventory item1 = new Inventory(1L, "Laptop", 10, 999.99);
        Inventory item2 = new Inventory(2L, "Phone", 20, 799.99);

        when(inventoryService.findAllInventory()).thenReturn(Arrays.asList(item1, item2));

        mockMvc.perform(get("/api/customer/inventory/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Laptop"))
                .andExpect(jsonPath("$[1].productName").value("Phone"));
    }

    @Test
    public void testGetInventoryById_Success() throws Exception {
        Inventory item = new Inventory(1L, "Laptop", 10, 999.99);

        when(inventoryService.findInventoryById(1L)).thenReturn(Optional.of(item));

        mockMvc.perform(get("/api/customer/inventory/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.price").value(999.99));
    }

    @Test
    public void testGetInventoryById_NotFound() throws Exception {
        when(inventoryService.findInventoryById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/customer/inventory/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
