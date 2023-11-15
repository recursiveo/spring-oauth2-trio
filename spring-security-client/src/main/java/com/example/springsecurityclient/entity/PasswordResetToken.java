package com.example.springsecurityclient.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PasswordResetToken {
        private static final int EXPIRY_TIME = 10;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String token;
        private Date expiryDate;
        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id",
                referencedColumnName = "id",
                nullable = false)
        private User user;


        // This to use the builder pattern, can be reduced to just the date calculation and constructor

        private PasswordResetToken(String token, Date expiryDate, User user) {
            this.token = token;
            this.expiryDate = expiryDate;
            this.user = user;
        }

public static class PasswordResetTokenBuilder {
    private String token;
    private final Date expiryDate = calculateExpiryDate(EXPIRY_TIME);
    private User user;

    public PasswordResetToken.PasswordResetTokenBuilder token(String token) {
        this.token = token;
        return this;
    }

    public PasswordResetToken.PasswordResetTokenBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    private Date calculateExpiryDate(int expiryTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expiryTime);
        return new Date(calendar.getTime().getTime());
    }

    public PasswordResetToken build() {
        return new PasswordResetToken(token, expiryDate, user);
    }
}

    public static PasswordResetToken.PasswordResetTokenBuilder builder() {
        return new PasswordResetToken.PasswordResetTokenBuilder();
    }
}
