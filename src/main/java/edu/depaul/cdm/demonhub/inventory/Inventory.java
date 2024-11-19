package edu.depaul.cdm.demonhub.inventory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents an inventory item with product details, quantity, and price")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the inventory item", example = "1")
    private Long id;

    @Column(name = "product_name", nullable = false, length = 100)
    @Schema(description = "Name of the product", example = "Laptop")
    private String productName;

    @Column(nullable = false)
    @Schema(description = "Quantity of the product available", example = "50")
    private Integer quantity;

    @Column(nullable = false)
    @Schema(description = "Price of the product", example = "999.99")
    private Double price;

    public Inventory(String productName, Integer quantity, Double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
