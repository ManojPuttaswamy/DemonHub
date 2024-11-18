package edu.depaul.cdm.demonhub.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoriesRepository;

    // Create a new category
    public Category createCategories(CategoryRequest req) {
        Category category = new Category();
        category.setCategoryName(req.getCategoryName());
        category.setDescription(req.getDescription());
        return categoriesRepository.save(category);
    }

    // Get category by name
    public Category getCategoryByCategoryName(String categoryName) {
        return categoriesRepository.findByCategoryName(categoryName);
    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categoriesRepository.findAll();
    }

    // Get category by ID
    public Category getCategoryById(Integer id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    // Update an existing category
    public void updateCategory(Integer id, CategoryRequest req) {
        Category existingCategory = getCategoryById(id);
        if (existingCategory != null) {
            existingCategory.setCategoryName(req.getCategoryName());
            existingCategory.setDescription(req.getDescription());
            categoriesRepository.save(existingCategory);
        } else {
            throw new IllegalArgumentException("Category with ID " + id + " not found.");
        }
    }

    // Delete a category by ID
    public void deleteCategory(Integer id) {
        if (categoriesRepository.existsById(id)) {
            categoriesRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Category with ID " + id + " not found.");
        }
    }
}
