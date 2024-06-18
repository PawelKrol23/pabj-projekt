package org.example.serwisogloszen.controller;

import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.model.dto.UserDTO;
import org.example.serwisogloszen.service.UserService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserController userController;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    @Test
    void testAddUser_WhenBindingResultHasErrors() {
        // given
        when(bindingResult.hasErrors()).thenReturn(true);

        // when
        String viewName = userController.addUser(new UserDTO(), bindingResult, model);

        // then
        assertEquals("user/register", viewName);
        verify(userService, never()).addUser(any(UserDTO.class));
    }

    @Test
    void testAddUser_WhenNoBindingResultErrors() {
        // given
        when(bindingResult.hasErrors()).thenReturn(false);

        // when
        String viewName = userController.addUser(new UserDTO(), bindingResult, model);

        // then
        assertEquals("redirect:/login", viewName);
        verify(userService, times(1)).addUser(any(UserDTO.class));
    }

    @Test
    void testUpdateUser_WhenBindingResultHasErrors() {
        // given
        when(bindingResult.hasErrors()).thenReturn(true);

        // when
        String viewName = userController.updateUser(new UserDTO(), bindingResult, model);

        // then
        assertEquals("user/edit", viewName);
        verify(userService, never()).updateUser(any(UserDTO.class));
    }

    @Test
    void testUpdateUser_WhenNoBindingResultErrors() {
        // given
        when(bindingResult.hasErrors()).thenReturn(false);

        // when
        String viewName = userController.updateUser(new UserDTO(), bindingResult, model);

        // then
        assertEquals("redirect:/user/profile", viewName);
        verify(userService, times(1)).updateUser(any(UserDTO.class));
    }
    @Test
    void testUserProfile() {
        // given
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        String username = "testUser";
        when(userService.getUser(username)).thenReturn(new UserEntity());

        // when
        String viewName = userController.userProfile(model);

        // then
        assertEquals("user/profile", viewName);
        verify(model, times(1)).addAttribute(eq("user"), any(UserEntity.class));
        verify(userService, times(1)).getUser(username);
    }

    @Test
    void testAddUserForm() {
        // when
        String viewName = userController.addUserForm(model);

        // then
        assertEquals("user/register", viewName);
        verify(model, times(1)).addAttribute(eq("user"), any(UserDTO.class));
    }

    @Test
    void testEditUserForm() {
        // given
        String username = "testUser";
        when(userService.getUser(username)).thenReturn(new UserEntity());

        // when
        String viewName = userController.editUserForm(model);

        // then
        assertEquals("user/edit", viewName);
        verify(model, times(1)).addAttribute(eq("user"), any(UserEntity.class));
        verify(userService, times(1)).getUser(username);
    }


}
