package org.example.serwisogloszen.service;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.Enum.Rola;
import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.model.dto.UserDTO;
import org.example.serwisogloszen.repository.UserRepository;
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
                .rola(Rola.USER)
                .build();
        return userRepository.save(newUser);
    }
}
