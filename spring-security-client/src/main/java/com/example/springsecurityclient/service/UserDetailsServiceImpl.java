package com.example.springsecurityclient.service;

import com.example.springsecurityclient.entity.User;
import com.example.springsecurityclient.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    private final UserRepository userRepository;


    public UserDetailsServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> optionalUser = userRepository.findByUsername(username);
//        User user = optionalUser.orElse(null);
//        LOG.info("Logging in user {}", username);
//        return user;
//    }
}
