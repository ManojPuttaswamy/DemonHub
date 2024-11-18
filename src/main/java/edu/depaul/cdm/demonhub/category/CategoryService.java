package edu.depaul.cdm.demonhub.category;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryRepository categoriesRepository;

    // Create a new category
    public Category createCategories(CategoryRequest req) {
        log.info("Request to create a new category: {}", req.getCategoryName());
        Category category = new Category();
        category.setCategoryName(req.getCategoryName());
        category.setDescription(req.getDescription());
        Category savedCategory = categoriesRepository.save(category);
        log.info("Category '{}' created successfully with ID: {}", savedCategory.getCategoryName(), savedCategory.getId());
        return savedCategory;
    }

    // Get category by name
    public Category getCategoryByCategoryName(String categoryName) {
        log.info("Fetching category by name: {}", categoryName);
        Category category = categoriesRepository.findByCategoryName(categoryName);
        if (category != null) {
            log.info("Category '{}' found with ID: {}", categoryName, category.getId());
        } else {
            log.warn("Category '{}' not found.", categoryName);
        }
        return category;
    }

    // Get all categories
    public List<Category> getAllCategories() {
        log.info("Fetching all categories");
        List<Category> categories = categoriesRepository.findAll();
        log.info("Retrieved {} categories", categories.size());
        return categories;
    }

    // Get category by ID
    public Category getCategoryById(Integer id) {
        log.info("Fetching category by ID: {}", id);
        Category category = categoriesRepository.findById(id).orElse(null);
        if (category != null) {
            log.info("Category with ID '{}' found: {}", id, category.getCategoryName());
        } else {
            log.warn("Category with ID '{}' not found.", id);
        }
        return category;
    }

    // Update an existing category
    public void updateCategory(Integer id, CategoryRequest req) {
        log.info("Request to update category with ID: {}", id);
        Category existingCategory = getCategoryById(id);
        if (existingCategory != null) {
            log.debug("Existing category details: {}", existingCategory);
            existingCategory.setCategoryName(req.getCategoryName());
            existingCategory.setDescription(req.getDescription());
            categoriesRepository.save(existingCategory);
            log.info("Category with ID '{}' updated successfully.", id);
        } else {
            log.error("Category with ID '{}' not found. Update failed.", id);
            throw new IllegalArgumentException("Category with ID " + id + " not found.");
        }
    }

    // Delete a category by ID
    public void deleteCategory(Integer id) {
        log.info("Request to delete category with ID: {}", id);
        if (categoriesRepository.existsById(id)) {
            categoriesRepository.deleteById(id);
            log.info("Category with ID '{}' deleted successfully.", id);
        } else {
            log.error("Category with ID '{}' not found. Deletion failed.", id);
            throw new IllegalArgumentException("Category with ID " + id + " not found.");
        }
    }
}
