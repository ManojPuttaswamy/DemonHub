package edu.depaul.cdm.demonhub.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Schema(description = "Represents a category with a name and description")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the category", example = "1")
    private Integer id;

    @Column(name = "category_name", nullable = false, length = 100)
    @Schema(description = "Name of the category", example = "Technology")
    private String categoryName;

    @Column(nullable = false)
    @Lob
    @Schema(description = "Description of the category", example = "Covers all technology-related topics")
    private String description;
}
