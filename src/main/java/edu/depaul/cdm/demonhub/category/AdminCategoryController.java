package edu.depaul.cdm.demonhub.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
@Tag(name = "Admin Categories", description = "Endpoints for managing categories by admin users")
@Slf4j
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    @Operation(summary = "Create a new category", description = "Allows admin to create a new category")
    public ResponseEntity<String> createCategory(@RequestBody CategoryRequest req) {
        log.info("Request received to create category: {}", req.getCategoryName());

        if (categoryService.getCategoryByCategoryName(req.getCategoryName()) != null) {
            log.warn("Category creation failed: {} already exists", req.getCategoryName());
            return ResponseEntity.badRequest().body("Category already exists");
        }

        categoryService.createCategories(req);
        log.info("Category {} created successfully", req.getCategoryName());
        return ResponseEntity.ok("Category created successfully");
    }

    @GetMapping("")
    @Operation(summary = "Get all categories", description = "Fetches a list of all categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("Fetching all categories");
        List<Category> categories = categoryService.getAllCategories();
        log.info("Retrieved {} categories", categories.size());
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category", description = "Updates the details of an existing category by its ID")
    public ResponseEntity<String> updateCategory(@PathVariable Integer id, @RequestBody CategoryRequest req) {
        log.info("Request received to update category with ID: {}", id);

        Category existingCategory = categoryService.getCategoryById(id);
        if (existingCategory == null) {
            log.error("Update failed: Category with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }

        categoryService.updateCategory(id, req);
        log.info("Category with ID {} updated successfully", id);
        return ResponseEntity.ok("Category updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category", description = "Deletes an existing category by its ID")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        log.info("Request received to delete category with ID: {}", id);

        Category existingCategory = categoryService.getCategoryById(id);
        if (existingCategory == null) {
            log.error("Delete failed: Category with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }

        categoryService.deleteCategory(id);
        log.info("Category with ID {} deleted successfully", id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a category by ID", description = "Fetches a category by its unique ID")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }
}
