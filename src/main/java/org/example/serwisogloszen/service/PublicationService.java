package org.example.serwisogloszen.service;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.exceptions.PublicationNotFoundException;
import org.example.serwisogloszen.exceptions.UserNotFoundException;
import org.example.serwisogloszen.model.EmailDetails;
import org.example.serwisogloszen.model.Publication;
import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.repository.CategoryRepository;
import org.example.serwisogloszen.repository.PublicationRepository;
import org.example.serwisogloszen.repository.UserRepository;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final JmsTemplate jmsTemplate;

    public List<Publication> getActualPublications() {
        return publicationRepository.findByModerationStateAndExpirationDateAfter(Publication.ModerationState.ACCEPTED, LocalDateTime.now());
    }
    public List<Publication> getOwnPublications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return publicationRepository.findByUser(user);
    }
    public Publication getPublicationById(Long publicationId) {
        return publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException(publicationId));
    }

    public Publication createNewPublication(PublicationDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        var newPublication = Publication.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(categoryRepository.findByName(dto.getCategoryName()))
                .user(user)
                .moderationState(Publication.ModerationState.WAITING_FOR_APPROVAL)
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
        foundPublication.setExpirationDate(LocalDateTime.now().plusDays(7));
        publicationRepository.save(foundPublication);

        var user = foundPublication.getUser();
        var emailDetails = EmailDetails.builder()
                .destinationMail(user.getEmail())
                .type(EmailDetails.Type.PUBLICATION_ACCEPTED)
                .publicationTitle(foundPublication.getTitle())
                .username(user.getLogin())
                .build();

        jmsTemplate.convertAndSend("mailbox", emailDetails);
    }

    public void rejectPublicationById(Long publicationId) {
        var foundPublication = getPublicationById(publicationId);
        foundPublication.setModerationState(Publication.ModerationState.REJECTED);
        publicationRepository.save(foundPublication);
    }
}
