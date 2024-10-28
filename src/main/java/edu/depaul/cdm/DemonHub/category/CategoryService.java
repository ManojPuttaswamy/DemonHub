package edu.depaul.cdm.demonhub.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoriesRepository;

    public Category createCategories(CategoryRequest req){
        Category categories = new Category();
        categories.setCategoryName(req.getCategoryName());
        categories.setDescription(req.getDescription());
        return categoriesRepository.save(categories);
    }

    public Category getCategoryByCategoryName(String categoryName){
        return categoriesRepository.findByCategoryName(categoryName);
    }

    public List<Category> getAllCategories(){
        return categoriesRepository.findAll();
    }
    
}
