package edu.depaul.cdm.demonhub.categories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    public List<Categories> ifIsActiveTrue();
}
