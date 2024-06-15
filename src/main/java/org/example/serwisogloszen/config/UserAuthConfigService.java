package org.example.serwisogloszen.config;

import org.example.serwisogloszen.model.UserEntity;
import org.example.serwisogloszen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthConfigService implements UserDetailsService {
    @Autowired
    UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = users.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        } else {
            UserDetails userDetails = User.withUsername(user.getLogin())
                    .password(user.getPassword())
                    .authorities(user.getRola())
                    .build();
            return userDetails;
        }
    }
}
