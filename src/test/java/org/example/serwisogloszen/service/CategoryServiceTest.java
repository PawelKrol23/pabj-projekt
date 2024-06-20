package org.example.serwisogloszen.service;

import org.example.serwisogloszen.exceptions.CategoryNotFoundException;
import org.example.serwisogloszen.model.Category;
import org.example.serwisogloszen.model.dto.CategoryDTO;
import org.example.serwisogloszen.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void testGetCategories() {
        // given
        List<Category> categories = List.of(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        // when
        List<Category> result = categoryService.getCategories();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testCreateNewCategory() {
        // given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("New Category");

        Category category = new Category();
        category.setName("New Category");

        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category savedCategory = invocation.getArgument(0);
            savedCategory.setId(1L);  // Simulate database generated ID
            return savedCategory;
        });

        // when
        Category result = categoryService.createNewCategory(categoryDTO);

        // then
        assertNotNull(result);
        assertEquals("New Category", result.getName());
        assertNotNull(result.getId());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testGetCategoryById() {
        // given
        Category category = new Category();
        category.setId(1L);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        // when
        Category result = categoryService.getCategoryById(1L);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateCategory() {
        // given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Updated Category");

        Category category = new Category();
        category.setId(1L);
        category.setName("Old Category");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Category result = categoryService.updateCategory(1L, categoryDTO);

        // then
        assertNotNull(result);
        assertEquals("Updated Category", result.getName());
        assertEquals(1L, result.getId());
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testDeleteCategoryById() {
        // given
        doNothing().when(categoryRepository).deleteById(anyLong());

        // when
        categoryService.deleteCategoryById(1L);

        // then
        verify(categoryRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetCategoryByIdThrowsException() {
        // given
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when / then
        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(1L));
        verify(categoryRepository, times(1)).findById(anyLong());
    }
}
