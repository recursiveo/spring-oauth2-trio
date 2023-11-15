package com.example.springsecurityclient.event.listener;

import com.example.springsecurityclient.entity.User;
import com.example.springsecurityclient.event.RegistrationCompleteEvent;
import com.example.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;

    public RegistrationCompleteEventListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //create the verification token for the user with link.
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVarificationToken(user, token);
        //send mail to user.
        String url = event.getApplicationUrl()
                        + "verifyUserEmail?token="
                        + token;

        //TODO: create actual email sending service
        log.info("The link to verify user email : {}", url);
    }
}
