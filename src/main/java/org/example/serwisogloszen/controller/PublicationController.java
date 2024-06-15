package org.example.serwisogloszen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.service.CategoryService;
import org.example.serwisogloszen.service.PublicationService;
import org.example.serwisogloszen.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;

    @GetMapping()
    public String listPublications(Model model) {
        model.addAttribute("publications", publicationService.getActualPublications());
        return "publication/listAll";
    }
    @GetMapping("/own")
    public String listOwnPublications(Model model) {
        model.addAttribute("publications", publicationService.getOwnPublications());
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
    public String editPublicationForm(@PathVariable("id") Long publicationId,
                                      Model model){
        var foundPublication = publicationService.getPublicationById(publicationId);
        if(foundPublication.getUser() != userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()))
        {
            return "redirect:/publications";
        }
        var publicationDto = PublicationDTO.builder()
                .title(foundPublication.getTitle())
                .description(foundPublication.getDescription())
                .build();

        model.addAttribute("publicationId", publicationId);
        model.addAttribute("publication", publicationDto);
        model.addAttribute("categories", categoryService.getCategories());
        return "publication/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPublication(@PathVariable("id") Long publicationId,
                                  @Valid @ModelAttribute("publication") PublicationDTO dto,
                                  BindingResult bindingResult,
                                  Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories());
            model.addAttribute("publicationId", publicationId);
            return "publication/edit";
        }

        publicationService.updatePublication(publicationId, dto);
        return "redirect:/publications";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long publicationId) {

        var foundPublication = publicationService.getPublicationById(publicationId);
        if(foundPublication.getUser() != userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()))
        {
            return "redirect:/publications";
        }
        publicationService.deletePublicationById(publicationId);
        return "redirect:/publications";
    }

    @GetMapping("/moderate")
    public String listPublicationsToModerate(Model model) {
        model.addAttribute("publications", publicationService.getPublicationsToModerate());
        return "publication/moderate";
    }

    @PostMapping("/moderate/{id}/accept")
    public String acceptPublication(@PathVariable("id") Long publicationId) {
        publicationService.acceptPublicationById(publicationId);
        return "redirect:/publications/moderate";
    }
}
