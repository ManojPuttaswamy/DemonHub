package edu.depaul.cdm.demonhub.category;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(nullable = false)
    @Lob
    private String description;

}
