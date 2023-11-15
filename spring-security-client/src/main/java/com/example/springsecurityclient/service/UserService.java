package com.example.springsecurityclient.service;


import com.example.springsecurityclient.model.LoginDTO;
import com.example.springsecurityclient.model.PasswordModel;
import com.example.springsecurityclient.model.RegisterDTO;
import com.example.springsecurityclient.model.UserDTO;
import com.example.springsecurityclient.entity.User;

public interface UserService {
    RegisterDTO register(UserDTO userDTO);

//    String login(LoginDTO loginDTO);

    void saveVarificationToken(User user, String token);

    String verifyToken(String token);

    String resetPassword(PasswordModel passwordModel);

    String verifyTokenAndChangePassword(String token, PasswordModel passwordModel);
}
