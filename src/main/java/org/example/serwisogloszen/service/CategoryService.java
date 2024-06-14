package org.example.serwisogloszen.service;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.Category;
import org.example.serwisogloszen.model.Publication;
import org.example.serwisogloszen.model.dto.CategoryDTO;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category createNewCategory(CategoryDTO dto) {
        var newCategory = Category.builder()
                .name(dto.getName())
                .build();

        return categoryRepository.save(newCategory);
    }
}
