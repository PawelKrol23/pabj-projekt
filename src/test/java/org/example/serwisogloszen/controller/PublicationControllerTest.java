package org.example.serwisogloszen.controller;

import org.example.serwisogloszen.model.Publication;
import org.example.serwisogloszen.service.PublicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicationControllerTest {

    @Mock
    PublicationService publicationService;

    @Mock
    Model model;

    @InjectMocks
    private PublicationController publicationController;

    @Test
    public void testListPublications() {
        // given
        List<Publication> publications = new ArrayList<>();
        when(publicationService.getActualPublications()).thenReturn(publications);

        // when
        String viewName = publicationController.listPublications(model);

        // then
        assertEquals("publication/listAll", viewName);
        verify(model, times(1)).addAttribute(eq("publications"), eq(publications));
        verify(publicationService, times(1)).getActualPublications();
    }

    @Test
    public void testListOwnPublications() {
        // given
        List<Publication> publications = new ArrayList<>();
        when(publicationService.getOwnPublications()).thenReturn(publications);

        // when
        String viewName = publicationController.listOwnPublications(model);

        // then
        assertEquals("publication/list", viewName);
        verify(model, times(1)).addAttribute(eq("publications"), eq(publications));
        verify(publicationService, times(1)).getOwnPublications();
    }
}
