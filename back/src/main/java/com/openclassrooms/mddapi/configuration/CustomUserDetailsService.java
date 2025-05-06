package com.openclassrooms.mddapi.configuration;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.DBUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DBUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
      DBUser user = userRepository.findByEmail(identifier)
        .orElseGet(() -> userRepository.findByUsername(identifier)
          .orElseThrow(() -> new UsernameNotFoundException("User not found with email or username: " + identifier)));

        return new org.springframework.security.core.userdetails.User(
            // user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            // Liste vide car pas de rôles
            Collections.emptyList()
        );
    }
}
