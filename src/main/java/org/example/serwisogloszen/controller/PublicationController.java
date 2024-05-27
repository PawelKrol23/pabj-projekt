package org.example.serwisogloszen.controller;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.service.PublicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;

    @GetMapping
    public String listPublications(Model model) {
        model.addAttribute("publications", publicationService.getAllPublications());
        return "publication/list";
    }
}
