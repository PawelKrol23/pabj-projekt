package org.example.serwisogloszen.config;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthConfigService implements UserDetailsService {
    private final UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = users.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        } else {
            return User.withUsername(user.getLogin())
                    .password(user.getPassword())
                    .authorities(user.getRole())
                    .build();
        }
    }
}
