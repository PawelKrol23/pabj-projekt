package org.example.serwisogloszen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.service.CategoryService;
import org.example.serwisogloszen.service.PublicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable("id") Long publicationId,
                               Model model){
        var foundPublication = publicationService.getPublicationById(publicationId);
        var publicationDto = PublicationDTO.builder()
                .title(foundPublication.getTitle())
                .description(foundPublication.getDescription())
                .build();

        model.addAttribute("publicationId", publicationId);
        model.addAttribute("publication", publicationDto);
        model.addAttribute("categories", categoryService.getCategories());
        return "publication/edit";
    }
}
