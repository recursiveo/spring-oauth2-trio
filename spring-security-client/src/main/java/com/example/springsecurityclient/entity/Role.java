package com.example.springsecurityclient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String authority;
}
