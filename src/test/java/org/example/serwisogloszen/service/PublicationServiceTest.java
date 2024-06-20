package org.example.serwisogloszen.service;

import org.example.serwisogloszen.exceptions.PublicationNotFoundException;
import org.example.serwisogloszen.model.Category;
import org.example.serwisogloszen.model.Publication;
import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.repository.CategoryRepository;
import org.example.serwisogloszen.repository.PublicationRepository;
import org.example.serwisogloszen.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicationServiceTest {

    @Mock
    private PublicationRepository publicationRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PublicationService publicationService;

    @BeforeEach
    void setUp() {
        // Setting up the security context for authenticated user

    }

    @Test
    void testGetActualPublications() {
        // given
        List<Publication> emptyList = List.of();
        when(publicationRepository.findByModerationStateAndExpirationDateAfter(eq(Publication.ModerationState.ACCEPTED), any()))
                .thenReturn(emptyList);

        // when
        List<Publication> result = publicationService.getActualPublications();

        // then
        assertTrue(result.isEmpty());
        verify(publicationRepository, times(1))
                .findByModerationStateAndExpirationDateAfter(eq(Publication.ModerationState.ACCEPTED), any());
    }

    @Test
    void testGetOwnPublications() {
        // given
        var authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("someName");
        var securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        UserEntity user = new UserEntity();
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(user));

        List<Publication> emptyList = List.of();
        when(publicationRepository.findByUser(eq(user))).thenReturn(emptyList);

        // when
        List<Publication> result = publicationService.getOwnPublications();

        // then
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByLogin(any());
        verify(publicationRepository, times(1))
                .findByUser(eq(user));
    }

    @Test
    void testGetPublicationById() {
        // given
        Publication publication = new Publication();
        when(publicationRepository.findById(anyLong())).thenReturn(Optional.of(publication));

        // when
        Publication result = publicationService.getPublicationById(1L);

        // then
        assertNotNull(result);
        verify(publicationRepository, times(1)).findById(anyLong());
    }

    @Test
    void testCreateNewPublication() {
        // given
        PublicationDTO dto = new PublicationDTO();
        dto.setTitle("Test Title");
        dto.setDescription("Test Description");
        dto.setCategoryName("Test Category");

        UserEntity user = new UserEntity();
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(user));
        when(categoryRepository.findByName(anyString())).thenReturn(new Category());

        Publication publication = new Publication();
        when(publicationRepository.save(any(Publication.class))).thenReturn(publication);

        // when
        Publication result = publicationService.createNewPublication(dto);

        // then
        assertNotNull(result);
        verify(userRepository, times(1)).findByLogin(anyString());
        verify(categoryRepository, times(1)).findByName(anyString());
        verify(publicationRepository, times(1)).save(any(Publication.class));
    }

    @Test
    void testUpdatePublication() {
        // given
        PublicationDTO dto = new PublicationDTO();
        dto.setTitle("Updated Title");
        dto.setDescription("Updated Description");
        dto.setCategoryName("Updated Category");

        Publication publication = new Publication();
        publication.setTitle("Old Title");
        publication.setDescription("Old Description");

        Category category = new Category();
        category.setName("Updated Category");

        when(publicationRepository.findById(anyLong())).thenReturn(Optional.of(publication));
        when(categoryRepository.findByName(anyString())).thenReturn(category);
        when(publicationRepository.save(any(Publication.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Publication result = publicationService.updatePublication(1L, dto);

        // then
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(category, result.getCategory());
        verify(publicationRepository, times(1)).findById(anyLong());
        verify(categoryRepository, times(1)).findByName(anyString());
        verify(publicationRepository, times(1)).save(any(Publication.class));
    }


    @Test
    void testDeletePublicationById() {
        // when
        publicationService.deletePublicationById(1L);

        // then
        verify(publicationRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetPublicationsToModerate() {
        // given
        List<Publication> emptyList = List.of();
        when(publicationRepository.findByModerationState(eq(Publication.ModerationState.WAITING_FOR_APPROVAL)))
                .thenReturn(emptyList);

        // when
        List<Publication> result = publicationService.getPublicationsToModerate();

        // then
        assertTrue(result.isEmpty());
        verify(publicationRepository, times(1)).findByModerationState(eq(Publication.ModerationState.WAITING_FOR_APPROVAL));
    }

//    @Test
//    void testAcceptPublicationById() {
//        // given
//        Publication publication = new Publication();
//        when(publicationRepository.findById(anyLong())).thenReturn(Optional.of(publication));
//
//        // when
//        publicationService.acceptPublicationById(1L);
//
//        // then
//        assertEquals(Publication.ModerationState.ACCEPTED, publication.getModerationState());
//        verify(publicationRepository, times(1)).findById(anyLong());
//        verify(publicationRepository, times(1)).save(any(Publication.class));
//    }
//    @Test
//    void testAcceptPublicationById() {
//        // given
//        UserEntity user = new UserEntity();
//        user.setEmail("test@example.com"); // Ensure the user has an email set
//
//        Publication publication = new Publication();
//        publication.setUser(user); // Set the user for the publication
//
//        when(publicationRepository.findById(anyLong())).thenReturn(Optional.of(publication));
//
//        // when
//        publicationService.acceptPublicationById(1L);
//
//        // then
//        assertEquals(Publication.ModerationState.ACCEPTED, publication.getModerationState());
//        assertNotNull(publication.getUser()); // Ensure user is not null
//        assertEquals("test@example.com", publication.getUser().getEmail()); // Verify user email
//        verify(publicationRepository, times(1)).findById(anyLong());
//        verify(publicationRepository, times(1)).save(any(Publication.class));
//    }

    @Test
    void testRejectPublicationById() {
        // given
        Publication publication = new Publication();
        when(publicationRepository.findById(anyLong())).thenReturn(Optional.of(publication));

        // when
        publicationService.rejectPublicationById(1L);

        // then
        assertEquals(Publication.ModerationState.REJECTED, publication.getModerationState());
        verify(publicationRepository, times(1)).findById(anyLong());
        verify(publicationRepository, times(1)).save(any(Publication.class));
    }
    @Test
    void testGetPublicationThrowsPublicationNotFoundException() {
        // given
        Long publicationId = 1L;
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.empty());

        // when / then
        assertThrows(PublicationNotFoundException.class, () -> {
            publicationService.getPublicationById(publicationId);
        });
    }
}
