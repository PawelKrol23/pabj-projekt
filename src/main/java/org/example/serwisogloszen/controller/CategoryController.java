package org.example.serwisogloszen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.dto.CategoryDTO;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return "category/list";
    }
    @GetMapping("/add")
    public String addCategoryForm(Model model){
        model.addAttribute("category", new CategoryDTO());
        return "category/add";
    }


    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") CategoryDTO dto,
                                 BindingResult bindingResult,
                                 Model model) {
        if(bindingResult.hasErrors()) {
            return "category/add";
        }
        categoryService.createNewCategory(dto);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id") Long categoryId,
                                      Model model){
        var foundCategory = categoryService.getCategoryById(categoryId);
        var categoryDto = CategoryDTO.builder()
                .name(foundCategory.getName())
                .build();

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("category", categoryDto);
        return "category/edit";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Long categoryId,
                                  @Valid @ModelAttribute("category") CategoryDTO dto,
                                  BindingResult bindingResult,
                                  Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("categoryId", categoryId);
            return "category/edit";
        }

        categoryService.updateCategory(categoryId, dto);
        return "redirect:/categories";
    }
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return "redirect:/categories";
    }
}
