package com.example.oauth2server.repository;

import com.example.oauth2server.entity.ServerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ServerUser, Long> {
    Optional<ServerUser> findByUsername(String username);

    Optional<ServerUser> findByEmail(String email);
}
