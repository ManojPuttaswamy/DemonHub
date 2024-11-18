package edu.depaul.cdm.demonhub.category;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CustomerCategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CustomerCategoryController customerCategoryController;

    @Test
    public void testGetAllCategories() {
        List<Category> categories = Arrays.asList(
            new Category(1, "Tech", "Tech Desc"),
            new Category(2, "Health", "Health Desc")
        );
        when(categoryService.getAllCategories()).thenReturn(categories);

        ResponseEntity<List<Category>> response = customerCategoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetCategoryById_Success() {
        Category category = new Category(1, "Tech", "Tech Desc");
        when(categoryService.getCategoryById(1)).thenReturn(category);

        ResponseEntity<Category> response = customerCategoryController.getCategoryById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetCategoryById_NotFound() {
        when(categoryService.getCategoryById(1)).thenReturn(null);

        ResponseEntity<Category> response = customerCategoryController.getCategoryById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateCategory_Success() {
        CategoryRequest request = new CategoryRequest();
        request.setCategoryName("New Tech");
        request.setDescription("Description");

        when(categoryService.getCategoryByCategoryName("New Tech")).thenReturn(null);
        when(categoryService.createCategories(request)).thenReturn(new Category());

        ResponseEntity<String> response = customerCategoryController.createCategory(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category created successfully", response.getBody());
    }

    @Test
    public void testCreateCategory_CategoryAlreadyExists() {
        CategoryRequest request = new CategoryRequest();
        request.setCategoryName("Tech");

        when(categoryService.getCategoryByCategoryName("Tech")).thenReturn(new Category());

        ResponseEntity<String> response = customerCategoryController.createCategory(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Category already exists", response.getBody());
    }

    @Test
    public void testUpdateCategory_Success() {
        Category existingCategory = new Category(1, "Tech", "Tech Desc");
        CategoryRequest request = new CategoryRequest();
        request.setCategoryName("Updated Tech");
        request.setDescription("Updated Desc");

        when(categoryService.getCategoryById(1)).thenReturn(existingCategory);

        ResponseEntity<String> response = customerCategoryController.updateCategory(1, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category updated successfully", response.getBody());
        verify(categoryService).updateCategory(1, request);
    }

    @Test
    public void testUpdateCategory_NotFound() {
        when(categoryService.getCategoryById(1)).thenReturn(null);

        ResponseEntity<String> response = customerCategoryController.updateCategory(1, new CategoryRequest());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteCategory_Success() {
        Category existingCategory = new Category(1, "Tech", "Tech Desc");

        when(categoryService.getCategoryById(1)).thenReturn(existingCategory);

        ResponseEntity<String> response = customerCategoryController.deleteCategory(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category deleted successfully", response.getBody());
        verify(categoryService).deleteCategory(1);
    }

    @Test
    public void testDeleteCategory_NotFound() {
        when(categoryService.getCategoryById(1)).thenReturn(null);

        ResponseEntity<String> response = customerCategoryController.deleteCategory(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
