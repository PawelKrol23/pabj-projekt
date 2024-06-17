package org.example.serwisogloszen.service;

import org.example.serwisogloszen.model.Publication;
import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.repository.PublicationRepository;
import org.example.serwisogloszen.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicationServiceTest {

    @Mock
    private PublicationRepository publicationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PublicationService publicationService;

    @Test
    void testGetActualPublications() {
            // given
            List<Publication> emptyList = List.of();
            when(publicationRepository
                    .findByModerationStateAndExpirationDateAfter(eq(Publication.ModerationState.ACCEPTED), any())
            ).thenReturn(emptyList);

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
        when(userRepository.findByLogin(any())).thenReturn(user);

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
}
