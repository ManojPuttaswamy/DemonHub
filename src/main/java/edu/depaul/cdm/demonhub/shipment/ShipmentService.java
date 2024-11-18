package edu.depaul.cdm.demonhub.shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    // Get all shipments
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    // Get a shipment by ID
    public Optional<Shipment> getShipmentById(String id) {
        return shipmentRepository.findById(id);
    }

    // Get shipments by order ID
    public List<Shipment> getShipmentsByOrderId(Long orderId) {
        return shipmentRepository.findByOrderId(orderId);
    }

    // Create a new shipment
    public Shipment createShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    // Update an existing shipment
    public Shipment updateShipment(String id, Shipment updatedShipment) {
        Optional<Shipment> existingShipment = shipmentRepository.findById(id);
        if (existingShipment.isPresent()) {
            Shipment shipment = existingShipment.get();
            shipment.setTrackingId(updatedShipment.getTrackingId());
            shipment.setOrderId(updatedShipment.getOrderId());
            shipment.setShippedDate(updatedShipment.getShippedDate());
            shipment.setExpectedDeliveryDate(updatedShipment.getExpectedDeliveryDate());
            shipment.setCurrentLocation(updatedShipment.getCurrentLocation());
            shipment.setStatus(updatedShipment.getStatus());
            return shipmentRepository.save(shipment);
        }
        throw new RuntimeException("Shipment not found with ID: " + id);
    }

    // Delete a shipment
    public void deleteShipment(String id) {
        shipmentRepository.deleteById(id);
    }
}