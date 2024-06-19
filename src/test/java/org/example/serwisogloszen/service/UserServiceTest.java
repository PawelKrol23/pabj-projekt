package org.example.serwisogloszen.service;

import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.model.dto.UserDTO;
import org.example.serwisogloszen.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUser() {
        // given
        UserEntity user = new UserEntity();
        user.setLogin("testUser");
        when(userRepository.findByLogin(anyString())).thenReturn(user);

        // when
        UserEntity result = userService.getUser("testUser");

        // then
        assertNotNull(result);
        assertEquals("testUser", result.getLogin());
        verify(userRepository, times(1)).findByLogin(anyString());
    }

    @Test
    void testAddUser() {
        // given
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("newUser");
        userDTO.setPassword("password");
        userDTO.setEmail("newUser@example.com");

        UserEntity userEntity = UserEntity.builder()
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .role(UserEntity.Role.USER)
                .build();

        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity savedUser = invocation.getArgument(0);
            savedUser.setId(1L); // Simulate database generated ID
            return savedUser;
        });

        // when
        UserEntity result = userService.addUser(userDTO);

        // then
        assertNotNull(result);
        assertEquals("newUser", result.getLogin());
        assertEquals("password", result.getPassword());
        assertEquals("newUser@example.com", result.getEmail());
        assertEquals(UserEntity.Role.USER, result.getRole());
        assertNotNull(result.getId());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testUpdateUser() {
        // given
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("updatedUser");
        userDTO.setPassword("newPassword");
        userDTO.setEmail("updatedUser@example.com");

        var authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("currentUser");
        var securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserEntity existingUser = UserEntity.builder()
                .login("currentUser")
                .password("oldPassword")
                .email("currentUser@example.com")
                .role(UserEntity.Role.USER)
                .build();

        when(userRepository.findByLogin(anyString())).thenReturn(existingUser);
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        UserEntity result = userService.updateUser(userDTO);

        // then
        assertNotNull(result);
        assertEquals("updatedUser", result.getLogin());
        assertEquals("newPassword", result.getPassword());
        assertEquals("updatedUser@example.com", result.getEmail());
        verify(userRepository, times(1)).findByLogin(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}
