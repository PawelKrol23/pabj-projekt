package org.example.serwisogloszen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.service.CategoryService;
import org.example.serwisogloszen.service.PublicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;
    private final CategoryService categoryService;

    @GetMapping
    public String listPublications(Model model) {
        model.addAttribute("publications", publicationService.getAllPublications());
        return "publication/list";
    }


    @GetMapping("/add")
    public String addPublicationForm(Model model){
        model.addAttribute("publication", new PublicationDTO());
        model.addAttribute("categories", categoryService.getCategories());
        return "publication/add";
    }


    @PostMapping("/add")
    public String addPublication(@Valid @ModelAttribute("publication") PublicationDTO dto,
                                 BindingResult bindingResult,
                                 Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories());
            return "publication/add";
        }

        publicationService.createNewPublication(dto);
        return "redirect:/publications";
    }
}
