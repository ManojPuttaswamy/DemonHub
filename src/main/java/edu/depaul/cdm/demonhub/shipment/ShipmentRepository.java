package edu.depaul.cdm.demonhub.shipment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends MongoRepository<Shipment, String> {

    // Find shipments by order ID
    List<Shipment> findByOrderId(Long orderId);

    // Find shipments by status
    List<Shipment> findByStatus(String status);
}