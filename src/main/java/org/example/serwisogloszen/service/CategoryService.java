package org.example.serwisogloszen.service;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.Category;
import org.example.serwisogloszen.model.Publication;
import org.example.serwisogloszen.model.dto.CategoryDTO;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.repository.CategoryRepository;
import org.example.serwisogloszen.repository.PublicationRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final PublicationRepository publicationRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category createNewCategory(CategoryDTO dto) {
        var newCategory = Category.builder()
                .name(dto.getName())
                .build();

        return categoryRepository.save(newCategory);
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }

    public Category updateCategory(Long categoryId, CategoryDTO dto) {
        var foundCategory= getCategoryById(categoryId);
        foundCategory.setName(dto.getName());
//        foundPublication.setDescription(dto.getDescription());
//        var foundCategory = categoryRepository.findByName(dto.getCategoryName());
//        foundPublication.setCategory(foundCategory);

        return categoryRepository.save(foundCategory);
    }
    public void deleteCategoryById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
