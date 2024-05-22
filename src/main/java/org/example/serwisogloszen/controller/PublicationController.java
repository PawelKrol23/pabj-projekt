package org.example.serwisogloszen.controller;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.service.PublicationService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;
}
