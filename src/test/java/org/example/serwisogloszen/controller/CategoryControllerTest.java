package org.example.serwisogloszen.controller;

import org.example.serwisogloszen.model.Category;
import org.example.serwisogloszen.model.dto.CategoryDTO;
import org.example.serwisogloszen.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private Model model;

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private BindingResult bindingResult;

    @Test
    void testListCategories() {
        // given
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        categories.add(category1);
        categories.add(category2);
        // Mockowanie serwisu
        when(categoryService.getCategories()).thenReturn(categories);

        // when
        String viewName = categoryController.listCategories(model);

        // then
        assertEquals("category/list", viewName);
        verify(model, times(1)).addAttribute(eq("categories"), anyList());
        verify(categoryService, times(1)).getCategories();
    }

    @Test
    void testAddCategory_WithErrors() {
        // given
        CategoryDTO categoryDTO = new CategoryDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        // when
        String viewName = categoryController.addCategory(categoryDTO, bindingResult, model);

        // then
        assertEquals("category/add", viewName);
        verifyNoInteractions(categoryService); // Nie powinno być wywołań do serwisu, bo są błędy walidacji
    }

    @Test
    void testAddCategory_Success() {
        // given
        CategoryDTO categoryDTO = new CategoryDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        // when
        String viewName = categoryController.addCategory(categoryDTO, bindingResult, model);

        // then
        assertEquals("redirect:/categories", viewName);
        verify(categoryService, times(1)).createNewCategory(categoryDTO);
    }

    @Test
    void testEditCategoryForm() {
        // given
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Category 1");

        when(categoryService.getCategoryById(categoryId)).thenReturn(category);

        // when
        String viewName = categoryController.editCategoryForm(categoryId, model);

        // then
        assertEquals("category/edit", viewName);
        verify(model, times(1)).addAttribute(eq("categoryId"), eq(categoryId));
        verify(model, times(1)).addAttribute(eq("category"), any(CategoryDTO.class));
    }

    @Test
    void testEditCategory_WithErrors() {
        // given
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        // when
        String viewName = categoryController.editCategory(categoryId, categoryDTO, bindingResult, model);

        // then
        assertEquals("category/edit", viewName);
        verify(model, times(1)).addAttribute(eq("categoryId"), eq(categoryId));
        verifyNoInteractions(categoryService); // Nie powinno być wywołań do serwisu, bo są błędy walidacji
    }

    @Test
    void testEditCategory_Success() {
        // given
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        // when
        String viewName = categoryController.editCategory(categoryId, categoryDTO, bindingResult, model);

        // then
        assertEquals("redirect:/categories", viewName);
        verify(categoryService, times(1)).updateCategory(categoryId, categoryDTO);
    }

    @Test
    void testDeleteCategory() {
        // given
        Long categoryId = 1L;

        // when
        String viewName = categoryController.deleteCategory(categoryId);

        // then
        assertEquals("redirect:/categories", viewName);
        verify(categoryService, times(1)).deleteCategoryById(eq(categoryId));
    }

    @Test
    void testAddCategoryForm() {
        // when
        String viewName = categoryController.addCategoryForm(model);

        // then
        assertEquals("category/add", viewName);
        verify(model, times(1)).addAttribute(eq("category"), any(CategoryDTO.class));
    }
}
