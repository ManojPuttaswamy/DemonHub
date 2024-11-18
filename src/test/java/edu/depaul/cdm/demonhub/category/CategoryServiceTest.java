
package edu.depaul.cdm.demonhub.category;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testCreateCategories() {
        CategoryRequest request = new CategoryRequest();
        request.setCategoryName("Tech");
        request.setDescription("Tech Desc");

        Category category = new Category();
        category.setCategoryName("Tech");
        category.setDescription("Tech Desc");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.createCategories(request);

        assertNotNull(result);
        assertEquals("Tech", result.getCategoryName());
    }

    @Test
    public void testGetCategoryByCategoryName() {
        Category category = new Category();
        category.setCategoryName("Tech");
        when(categoryRepository.findByCategoryName("Tech")).thenReturn(category);

        Category result = categoryService.getCategoryByCategoryName("Tech");

        assertNotNull(result);
        assertEquals("Tech", result.getCategoryName());
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = Arrays.asList(new Category(1, "Tech", "Tech Desc"), new Category(2, "Health", "Health Desc"));
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
    }
}
