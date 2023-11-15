package com.example.springsecurityclient.config;

import com.example.springsecurityclient.entity.Role;
import com.example.springsecurityclient.entity.User;
import com.example.springsecurityclient.repository.RoleRepository;
import com.example.springsecurityclient.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Role adminRole = Role.builder()
                .authority("ROLE_ADMIN")
                .build();

        Role userRole = Role.builder()
                .authority("ROLE_USER")
                .build();

        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .email("admin@example.net")
                .authorities(Set.of(adminRole))
                .enabled(true)
                .build();

        try{
            roleRepository.saveAll(List.of(adminRole,userRole));
            userRepository.save(admin);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Admin user saved, roles saved.");
            }

        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Some error on saving admin user and roles", ex);
        }



    }
}
