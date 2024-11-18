package edu.depaul.cdm.demonhub.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Categories", description = "Endpoints for managing categories by admin users")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    @Operation(summary = "Create a new category", description = "Allows admin to create a new category")
    public ResponseEntity<String> createCategories(@RequestBody CategoryRequest req) {
        if (categoryService.getCategoryByCategoryName(req.getCategoryName()) != null) {
            return ResponseEntity.badRequest().body("Category already exists");
        }
        categoryService.createCategories(req);
        return ResponseEntity.ok("Category created successfully");
    }

    @GetMapping("")
    @Operation(summary = "Get all categories", description = "Fetches a list of all categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
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

    @PutMapping("/{id}")
    @Operation(summary = "Update a category", description = "Updates the details of an existing category by its ID")
    public ResponseEntity<String> updateCategory(@PathVariable Integer id, @RequestBody CategoryRequest req) {
        Category existingCategory = categoryService.getCategoryById(id);
        if (existingCategory == null) {
            return ResponseEntity.notFound().build();
        }
        categoryService.updateCategory(id, req);
        return ResponseEntity.ok("Category updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category", description = "Deletes an existing category by its ID")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        Category existingCategory = categoryService.getCategoryById(id);
        if (existingCategory == null) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
