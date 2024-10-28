package edu.depaul.cdm.demonhub.reviews;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "reviews")

public class Reviews {
    @Id
    private Long id;
}
