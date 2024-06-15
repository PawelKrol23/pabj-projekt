package org.example.serwisogloszen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/categories").hasAnyAuthority("ADMIN")
                        .requestMatchers("/categories/add").hasAnyAuthority("ADMIN")
                        .requestMatchers("/categories/edit/*").hasAnyAuthority("ADMIN")
                        .requestMatchers("/categories/delete/*").hasAnyAuthority("ADMIN")
                        .requestMatchers("/publications/moderate").hasAnyAuthority("ADMIN")
                        .requestMatchers("/publications/moderate/**").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .logout(logout -> {
                    logout.logoutSuccessUrl("/").permitAll();
                })
                .formLogin(login -> {
                    login.defaultSuccessUrl("/publications/own", true).permitAll();
                });

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager (UserDetailsService customUserDetailsService)
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider .setUserDetailsService(customUserDetailsService);
        // authProvider .setPasswordEncoder(passwordEncoder());
        List<AuthenticationProvider> providers = List.of(authProvider );
        return new ProviderManager(providers);
    }

}
