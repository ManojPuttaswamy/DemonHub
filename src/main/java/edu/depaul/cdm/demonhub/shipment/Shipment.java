package edu.depaul.cdm.demonhub.shipment;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Document(collection = "shipments")
@Data
public class Shipment {

    @Id
    private String id; // MongoDB uses String IDs

    private String trackingId;
    private Long orderId; // Reference to the relational Order entity
    private String shippedDate;
    private String expectedDeliveryDate;
    private String currentLocation;
    private String status; // Example: In-Transit, Delivered
}