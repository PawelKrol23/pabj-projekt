package org.example.serwisogloszen.service;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.model.dto.UserDTO;
import org.example.serwisogloszen.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public UserEntity getUser(String nazwa){return userRepository.findByLogin(nazwa);}

    public UserEntity addUser(UserDTO dto)
    {
        var newUser = UserEntity.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .role(UserEntity.Role.USER)
                .build();
        return userRepository.save(newUser);
    }
    public UserEntity updateUser(UserDTO dto)
    {
        var foundUser = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        foundUser.setLogin(dto.getLogin());
        foundUser.setEmail(dto.getEmail());
        foundUser.setPassword(dto.getPassword());
        return userRepository.save(foundUser);
    }
}
