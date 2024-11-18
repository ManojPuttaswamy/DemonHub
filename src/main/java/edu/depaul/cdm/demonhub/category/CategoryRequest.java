package edu.depaul.cdm.demonhub.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request body for creating or updating a category")
public class CategoryRequest {

    @Schema(description = "Unique identifier of the category", example = "1")
    private int id;

    @Schema(description = "Name of the category", example = "Technology")
    private String categoryName;

    @Schema(description = "Description of the category", example = "Covers all technology-related topics")
    private String description;
}
