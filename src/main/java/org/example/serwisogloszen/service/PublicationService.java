package org.example.serwisogloszen.service;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.Publication;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.repository.CategoryRepository;
import org.example.serwisogloszen.repository.PublicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final CategoryRepository categoryRepository;

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    public Publication createNewPublication(PublicationDTO dto) {
        var newPublication = Publication.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(categoryRepository.findByName(dto.getCategoryName()))
                .build();

        return publicationRepository.save(newPublication);
    }
}
