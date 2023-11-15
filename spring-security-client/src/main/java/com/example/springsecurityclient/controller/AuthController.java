package com.example.springsecurityclient.controller;

import com.example.springsecurityclient.model.PasswordModel;
import com.example.springsecurityclient.model.RegisterDTO;
import com.example.springsecurityclient.model.UserDTO;
import com.example.springsecurityclient.event.RegistrationCompleteEvent;
import com.example.springsecurityclient.service.UserService;
import com.example.springsecurityclient.utils.UtilClass;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Slf4j
@RestController
public class AuthController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    private final WebClient webClient;

    public AuthController(UserService userService, ApplicationEventPublisher publisher, WebClient webClient) {
        this.userService = userService;
        this.publisher = publisher;
        this.webClient = webClient;
    }

    @PostMapping("/register")
    public String register(@RequestBody UserDTO userDTO, final HttpServletRequest request) {

        RegisterDTO registerDTO = userService.register(userDTO);

        if(registerDTO.getUser() != null) {
            publisher.publishEvent(new RegistrationCompleteEvent(
                    registerDTO.getUser(),
                    UtilClass.getAppUrl(request)
            ));

            return "User  Registration Successful.";
        }
        return "User Registration failed \n" + registerDTO.getErrorMessage();
    }

    @GetMapping("/auth/home")
    public String auth(Authentication auth) {
        return "Inside auth controller " + auth.getName();
    }

    @GetMapping("/verifyUserEmail")
    public String verifyToken(@RequestParam String token) {
       return userService.verifyToken(token);
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, final HttpServletRequest request) {
        String token = userService.resetPassword(passwordModel);
        if(token != null) {
           String url = UtilClass.getPasswordResetLink(request, token);

           log.info(url); //TODO: create an actual email sender

           return "Password reset link sent to registered email";
        }
        return "Invalid email";
    }

    @PostMapping("/createNewPassword")
    public String createNewPassword(@RequestParam String token, @RequestBody PasswordModel passwordModel) {
        return userService.verifyTokenAndChangePassword(token, passwordModel);
    }

    @GetMapping("/api/resource")
    public String resource(@RegisteredOAuth2AuthorizedClient("api-client-authorization-code") OAuth2AuthorizedClient client) {
        return webClient
                .get()
                .uri("http://127.0.0.1:8090/api/")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
