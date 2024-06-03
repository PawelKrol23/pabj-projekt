package org.example.serwisogloszen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.Publication;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.service.CategoryService;
import org.example.serwisogloszen.service.PublicationService;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                          BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "redirect:/publications/add";
        }

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserEntity loggedUser = userService.getUserByName(authentication.getName());
//
//        Category category = categoryService.getCategoryByName(dto.getCategoryName(), loggedUser.getId());
//        Status status = statusService.getStatusByName(dto.getStatusName(), loggedUser.getId());
//
//
//        Task task = new Task();
//        task.setOwner(userService.getUserById(loggedUser.getId()));
//        task.setSummary(dto.getSummary());
//        task.setDescription(dto.getDescription());
//        task.setStartDate(LocalDateTime.parse(dto.getStartDate()));
//        task.setEndDate(LocalDateTime.parse(dto.getEndDate()));
//        task.setTaskCategory(category);
//        task.setStatus(status);
//        taskService.save(task);

        return "redirect:/publications";
    }
}
