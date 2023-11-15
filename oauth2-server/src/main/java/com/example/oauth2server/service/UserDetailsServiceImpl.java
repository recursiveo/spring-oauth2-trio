package com.example.oauth2server.service;


import com.example.oauth2server.entity.ServerUser;
import com.example.oauth2server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    private final UserRepository userRepository;


    public UserDetailsServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ServerUser> optionalUser = userRepository.findByUsername(username);
        ServerUser user = optionalUser.orElse(null);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        LOG.info("Logging in user {}", username);
        return user;
    }
}
