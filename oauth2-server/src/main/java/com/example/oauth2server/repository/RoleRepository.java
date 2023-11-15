package com.example.oauth2server.repository;

import com.example.oauth2server.entity.ServerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<ServerRole, Long> {
    Optional<ServerRole> findByAuthority(String authority);
}
