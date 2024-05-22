package org.example.serwisogloszen.controller;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.service.CategoryService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
}
