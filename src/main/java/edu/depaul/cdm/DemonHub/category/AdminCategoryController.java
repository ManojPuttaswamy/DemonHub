package edu.depaul.cdm.demonhub.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<String> createCategories(@RequestBody CategoryRequest req){
        
        if(categoryService.getCategoryByCategoryName(req.getCategoryName())!= null){
            return ResponseEntity.badRequest().body("Category already exists");
        }

        categoryService.createCategories(req);
        return ResponseEntity.ok("Category created successfully");
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    
    
}
