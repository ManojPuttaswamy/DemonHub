package edu.depaul.cdm.demonhub.shipment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private ShipmentService shipmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllShipments() {
        Shipment shipment1 = new Shipment();
        shipment1.setId("1");
        shipment1.setOrderId(101L);

        Shipment shipment2 = new Shipment();
        shipment2.setId("2");
        shipment2.setOrderId(102L);

        when(shipmentRepository.findAll()).thenReturn(Arrays.asList(shipment1, shipment2));

        List<Shipment> shipments = shipmentService.getAllShipments();
        assertEquals(2, shipments.size());
        assertEquals("1", shipments.get(0).getId());
    }

    @Test
    public void testCreateShipment() {
        Shipment shipment = new Shipment();
        shipment.setOrderId(103L);
        shipment.setTrackingId("789");

        when(shipmentRepository.save(any(Shipment.class))).thenReturn(shipment);

        Shipment createdShipment = shipmentService.createShipment(shipment);
        assertNotNull(createdShipment);
        assertEquals("789", createdShipment.getTrackingId());
    }
}