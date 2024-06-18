package org.example.serwisogloszen.controller;

import org.aspectj.lang.annotation.Before;
import org.example.serwisogloszen.model.Publication;
import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.model.dto.PublicationDTO;
import org.example.serwisogloszen.service.CategoryService;
import org.example.serwisogloszen.service.PublicationService;
import org.example.serwisogloszen.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicationControllerTest {

    @Mock
    private PublicationService publicationService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private PublicationController publicationController;

    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;



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
    @Test
    void testAddPublicationForm() {
        when(categoryService.getCategories()).thenReturn(new ArrayList<>());

        String viewName = publicationController.addPublicationForm(model);

        assertEquals("publication/add", viewName);
        verify(model, times(1)).addAttribute(eq("publication"), any(PublicationDTO.class));
        verify(model, times(1)).addAttribute(eq("categories"), anyList());
    }

    @Test
    void testAddPublication_WithErrors() {
        PublicationDTO publicationDTO = new PublicationDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(categoryService.getCategories()).thenReturn(new ArrayList<>());

        String viewName = publicationController.addPublication(publicationDTO, bindingResult, model);

        assertEquals("publication/add", viewName);
        verify(model, times(1)).addAttribute(eq("categories"), anyList());
        verify(publicationService, times(0)).createNewPublication(any(PublicationDTO.class));
    }

    @Test
    void testAddPublication_Success() {
        PublicationDTO publicationDTO = new PublicationDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = publicationController.addPublication(publicationDTO, bindingResult, model);

        assertEquals("redirect:/publications", viewName);
        verify(publicationService, times(1)).createNewPublication(publicationDTO);
    }

    @Test
    void testEditPublicationForm_Unauthorized() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        Publication publication = new Publication();
        publication.setUser(user);

        when(publicationService.getPublicationById(anyLong())).thenReturn(publication);
        when(userService.getUser(anyString())).thenReturn(new UserEntity());

        String viewName = publicationController.editPublicationForm(1L, model);

        assertEquals("redirect:/publications", viewName);
    }

    @Test
    void testEditPublicationForm_Authorized() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        Publication publication = new Publication();
        publication.setUser(user);

        when(publicationService.getPublicationById(anyLong())).thenReturn(publication);
        when(userService.getUser(anyString())).thenReturn(user);
        when(categoryService.getCategories()).thenReturn(new ArrayList<>());

        String viewName = publicationController.editPublicationForm(1L, model);

        assertEquals("publication/edit", viewName);
        verify(model, times(1)).addAttribute(eq("publicationId"), eq(1L));
        verify(model, times(1)).addAttribute(eq("publication"), any(PublicationDTO.class));
        verify(model, times(1)).addAttribute(eq("categories"), anyList());
    }

    @Test
    void testEditPublication_WithErrors() {
        PublicationDTO publicationDTO = new PublicationDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(categoryService.getCategories()).thenReturn(new ArrayList<>());

        String viewName = publicationController.editPublication(1L, publicationDTO, bindingResult, model);

        assertEquals("publication/edit", viewName);
        verify(model, times(1)).addAttribute(eq("categories"), anyList());
        verify(model, times(1)).addAttribute(eq("publicationId"), eq(1L));
        verify(publicationService, times(0)).updatePublication(anyLong(), any(PublicationDTO.class));
    }

    @Test
    void testEditPublication_Success() {
        PublicationDTO publicationDTO = new PublicationDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = publicationController.editPublication(1L, publicationDTO, bindingResult, model);

        assertEquals("redirect:/publications", viewName);
        verify(publicationService, times(1)).updatePublication(eq(1L), eq(publicationDTO));
    }

    @Test
    void testDeleteTask_Unauthorized() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        Publication publication = new Publication();
        publication.setUser(user);

        when(publicationService.getPublicationById(anyLong())).thenReturn(publication);
        when(userService.getUser(anyString())).thenReturn(new UserEntity());

        String viewName = publicationController.deleteTask(1L);

        assertEquals("redirect:/publications", viewName);
        verify(publicationService, times(0)).deletePublicationById(anyLong());
    }

    @Test
    void testDeleteTask_Authorized() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        Publication publication = new Publication();
        publication.setUser(user);

        when(publicationService.getPublicationById(anyLong())).thenReturn(publication);
        when(userService.getUser(anyString())).thenReturn(user);

        String viewName = publicationController.deleteTask(1L);

        assertEquals("redirect:/publications", viewName);
        verify(publicationService, times(1)).deletePublicationById(eq(1L));
    }

    @Test
    void testListPublicationsToModerate() {
        // given
        List<Publication> publications = new ArrayList<>(); // przygotowanie listy publikacji do moderacji
        when(publicationService.getPublicationsToModerate()).thenReturn(publications);

        // when
        String viewName = publicationController.listPublicationsToModerate(model);

        // then
        assertEquals("publication/moderate", viewName);
        verify(model, times(1)).addAttribute(eq("publications"), eq(publications));
        verify(publicationService, times(1)).getPublicationsToModerate();
    }

    @Test
    void testAcceptPublication() {
        // given
        Long publicationId = 1L;

        // when
        String redirectUrl = publicationController.acceptPublication(publicationId);

        // then
        assertEquals("redirect:/publications/moderate", redirectUrl);
        verify(publicationService, times(1)).acceptPublicationById(eq(publicationId));
    }

    @Test
    void testRejectPublication() {
        // given
        Long publicationId = 1L;

        // when
        String redirectUrl = publicationController.rejectPublication(publicationId);

        // then
        assertEquals("redirect:/publications/moderate", redirectUrl);
        verify(publicationService, times(1)).rejectPublicationById(eq(publicationId));
    }

}
