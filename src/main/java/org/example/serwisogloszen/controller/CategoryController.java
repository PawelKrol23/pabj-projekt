package org.example.serwisogloszen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.dto.CategoryDTO;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/add")
    public String addPublicationForm(Model model){
        model.addAttribute("category", new CategoryDTO());
        return "category/add";
    }


    @PostMapping("/add")
    public String addPublication(@Valid @ModelAttribute("category") CategoryDTO dto,
                                 BindingResult bindingResult,
                                 Model model) {
        if(bindingResult.hasErrors()) {
            return "category/add";
        }
        categoryService.createNewCategory(dto);
        return "redirect:/publications";
    }
}
