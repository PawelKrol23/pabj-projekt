package org.example.serwisogloszen.service;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    public UserEntity getUser(String nazwa){return userRepository.findByLogin(nazwa);}
}
