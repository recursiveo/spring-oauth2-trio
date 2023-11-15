package com.example.springsecurityclient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "app_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_email", columnNames = "email")
        })
public class User{

    @Id
    @SequenceGenerator(name = "user_id", sequenceName = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
    private Long id;
    private String username;
    private String password;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> authorities;
    private boolean enabled = false;

}
