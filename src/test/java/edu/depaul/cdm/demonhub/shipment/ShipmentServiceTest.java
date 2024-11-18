package edu.depaul.cdm.demonhub.shipment;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShipmentServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShipmentService shipmentService;

    private static final String BASE_URL = "/api/shipments";

    @Test
    public void testGetAllShipments() throws Exception {
        List<Shipment> shipments = Arrays.asList(
                new Shipment("1", 101L, "123", "Location 1", "2024-11-20", "2024-11-25", "In Transit"),
                new Shipment("2", 102L, "456", "Location 2", "2024-11-15", "2024-11-18", "Delivered")
        );

        when(shipmentService.getAllShipments()).thenReturn(shipments);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].trackingId", is("123")))
                .andExpect(jsonPath("$[1].trackingId", is("456")));
    }

    @Test
    public void testCreateShipment() throws Exception {
        Shipment shipment = new Shipment(null, 103L, "789", "Location 3", "2024-11-22", "2024-11-29", "Pending");
        Shipment savedShipment = new Shipment("3", 103L, "789", "Location 3", "2024-11-22", "2024-11-29", "Pending");

        when(shipmentService.createShipment(Mockito.any(Shipment.class))).thenReturn(savedShipment);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderId\":103,\"trackingId\":\"789\",\"currentLocation\":\"Location 3\",\"shippedDate\":\"2024-11-22\",\"expectedDeliveryDate\":\"2024-11-29\",\"status\":\"Pending\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("3")))
                .andExpect(jsonPath("$.trackingId", is("789")));
    }

    @Test
    public void testGetShipmentById_NotFound() throws Exception {
        when(shipmentService.getShipmentById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteShipment_Success() throws Exception {
        Mockito.doNothing().when(shipmentService).deleteShipment("1");

        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Shipment deleted successfully"));
    }
}