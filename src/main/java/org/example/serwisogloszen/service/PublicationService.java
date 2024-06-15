package org.example.serwisogloszen.service;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.Publication;
import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.repository.CategoryRepository;
import org.example.serwisogloszen.repository.PublicationRepository;
import org.example.serwisogloszen.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<Publication> getActualPublications() {
        return publicationRepository.findByModerationState(Publication.ModerationState.ACCEPTED);
    }
    public List<Publication> getOwnPublications() {
        UserEntity user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.getPublications();
    }
    public Publication getPublicationById(Long publicationId) {
        return publicationRepository.findById(publicationId).orElseThrow();
    }

    public Publication createNewPublication(PublicationDTO dto) {
        var newPublication = Publication.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(categoryRepository.findByName(dto.getCategoryName()))
                .user(userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()))
                .build();

        return publicationRepository.save(newPublication);
    }

    public Publication updatePublication(Long publicationId, PublicationDTO dto) {
        var foundPublication = getPublicationById(publicationId);
        foundPublication.setTitle(dto.getTitle());
        foundPublication.setDescription(dto.getDescription());
        var foundCategory = categoryRepository.findByName(dto.getCategoryName());
        foundPublication.setCategory(foundCategory);

        return publicationRepository.save(foundPublication);
    }

    public void deletePublicationById(Long publicationId) {
        publicationRepository.deleteById(publicationId);
    }

    public List<Publication> getPublicationsToModerate() {
        return publicationRepository.findByModerationState(Publication.ModerationState.WAITING_FOR_APPROVAL);
    }

    public void acceptPublicationById(Long publicationId) {
        var foundPublication = getPublicationById(publicationId);
        foundPublication.setModerationState(Publication.ModerationState.ACCEPTED);
        publicationRepository.save(foundPublication);
    }
}
