package com.example.springsecurityclient.model;

import com.example.springsecurityclient.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDTO {
    User user;
    String errorMessage;
}
