package org.example.serwisogloszen.service;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
}
