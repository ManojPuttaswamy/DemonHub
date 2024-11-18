package edu.depaul.cdm.demonhub.shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    // Get all shipments
    public List<Shipment> getAllShipments() {
        log.info("Fetching all shipments");
        List<Shipment> shipments = shipmentRepository.findAll();
        log.info("Retrieved {} shipments from the database", shipments.size());
        return shipments;
    }

    // Get a shipment by ID
    public Optional<Shipment> getShipmentById(String id) {
        log.info("Fetching shipment with ID: {}", id);
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        if (shipment.isPresent()) {
            log.info("Shipment with ID {} found", id);
        } else {
            log.warn("Shipment with ID {} not found", id);
        }
        return shipment;
    }

    // Get shipments by order ID
    public List<Shipment> getShipmentsByOrderId(Long orderId) {
        log.info("Fetching shipments for order ID: {}", orderId);
        List<Shipment> shipments = shipmentRepository.findByOrderId(orderId);
        log.info("Retrieved {} shipments for order ID: {}", shipments.size(), orderId);
        return shipments;
    }

    // Create a new shipment
    public Shipment createShipment(Shipment shipment) {
        log.info("Request received to create a new shipment for order ID: {}", shipment.getOrderId());
        Shipment savedShipment = shipmentRepository.save(shipment);
        log.info("Shipment created successfully with ID: {}", savedShipment.getId());
        return savedShipment;
    }

    // Update an existing shipment
    public Shipment updateShipment(String id, Shipment updatedShipment) {
        log.info("Request received to update shipment with ID: {}", id);
        Optional<Shipment> existingShipment = shipmentRepository.findById(id);
        if (existingShipment.isPresent()) {
            Shipment shipment = existingShipment.get();
            log.debug("Existing shipment details: {}", shipment);

            shipment.setTrackingId(updatedShipment.getTrackingId());
            shipment.setOrderId(updatedShipment.getOrderId());
            shipment.setShippedDate(updatedShipment.getShippedDate());
            shipment.setExpectedDeliveryDate(updatedShipment.getExpectedDeliveryDate());
            shipment.setCurrentLocation(updatedShipment.getCurrentLocation());
            shipment.setStatus(updatedShipment.getStatus());

            Shipment savedShipment = shipmentRepository.save(shipment);
            log.info("Shipment with ID {} updated successfully", id);
            return savedShipment;
        }
        log.error("Update failed: Shipment with ID {} not found", id);
        throw new RuntimeException("Shipment not found with ID: " + id);
    }

    // Delete a shipment
    public void deleteShipment(String id) {
        log.info("Request received to delete shipment with ID: {}", id);
        if (shipmentRepository.existsById(id)) {
            shipmentRepository.deleteById(id);
            log.info("Shipment with ID {} deleted successfully", id);
        } else {
            log.error("Delete failed: Shipment with ID {} not found", id);
            throw new RuntimeException("Shipment not found with ID: " + id);
        }
    }
}