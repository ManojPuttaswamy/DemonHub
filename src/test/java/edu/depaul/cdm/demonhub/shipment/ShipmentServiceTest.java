package edu.depaul.cdm.demonhub.shipment;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Shipment shipment1 = new Shipment();
        shipment1.setId("1");
        shipment1.setOrderId(101L);
        shipment1.setTrackingId("123");
        shipment1.setCurrentLocation("Location 1");
        shipment1.setShippedDate("2024-11-20");
        shipment1.setExpectedDeliveryDate("2024-11-25");
        shipment1.setStatus("In Transit");

        Shipment shipment2 = new Shipment();
        shipment2.setId("2");
        shipment2.setOrderId(102L);
        shipment2.setTrackingId("456");
        shipment2.setCurrentLocation("Location 2");
        shipment2.setShippedDate("2024-11-15");
        shipment2.setExpectedDeliveryDate("2024-11-18");
        shipment2.setStatus("Delivered");

        List<Shipment> shipments = Arrays.asList(shipment1, shipment2);

        when(shipmentService.getAllShipments()).thenReturn(shipments);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].trackingId", is("123")))
                .andExpect(jsonPath("$[1].trackingId", is("456")));
    }

    @Test
    public void testCreateShipment() throws Exception {
        Shipment shipment = new Shipment();
        shipment.setOrderId(103L);
        shipment.setTrackingId("789");
        shipment.setCurrentLocation("Location 3");
        shipment.setShippedDate("2024-11-22");
        shipment.setExpectedDeliveryDate("2024-11-29");
        shipment.setStatus("Pending");

        Shipment savedShipment = new Shipment();
        savedShipment.setId("3");
        savedShipment.setOrderId(103L);
        savedShipment.setTrackingId("789");
        savedShipment.setCurrentLocation("Location 3");
        savedShipment.setShippedDate("2024-11-22");
        savedShipment.setExpectedDeliveryDate("2024-11-29");
        savedShipment.setStatus("Pending");

        when(shipmentService.createShipment(Mockito.any(Shipment.class))).thenReturn(savedShipment);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderId\":103,\"trackingId\":\"789\",\"currentLocation\":\"Location 3\",\"shippedDate\":\"2024-11-22\",\"expectedDeliveryDate\":\"2024-11-29\",\"status\":\"Pending\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("3")))
                .andExpect(jsonPath("$.trackingId", is("789")));
    }
}