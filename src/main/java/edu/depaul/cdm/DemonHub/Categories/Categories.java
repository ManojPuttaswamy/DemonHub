package edu.depaul.cdm.demonhub.categories;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private java.time.LocalDateTime createdDate;

    @Column(nullable = false)
    private Boolean isActive;

}
