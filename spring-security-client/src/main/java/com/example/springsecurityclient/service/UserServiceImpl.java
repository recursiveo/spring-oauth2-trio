package com.example.springsecurityclient.service;

import com.example.springsecurityclient.entity.PasswordResetToken;
import com.example.springsecurityclient.model.LoginDTO;
import com.example.springsecurityclient.model.PasswordModel;
import com.example.springsecurityclient.model.RegisterDTO;
import com.example.springsecurityclient.model.UserDTO;
import com.example.springsecurityclient.entity.Role;
import com.example.springsecurityclient.entity.User;
import com.example.springsecurityclient.entity.VerificationToken;
import com.example.springsecurityclient.repository.PasswordResetTokenRepository;
import com.example.springsecurityclient.repository.RoleRepository;
import com.example.springsecurityclient.repository.UserRepository;
import com.example.springsecurityclient.repository.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;


//    private final AuthenticationManager manager;
    //private final TokenService tokenService;

    public UserServiceImpl(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
//        this.manager = manager;
        //this.tokenService = tokenService;
    }

    @Override
    public RegisterDTO register(UserDTO userDTO) {
        Optional<Role> optionalRole = roleRepository.findByAuthority("ROLE_USER");
        Role userRole = optionalRole.orElse(null);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userRole);

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .authorities(roleSet)
                .build();

        try{
            userRepository.save(user);
            return RegisterDTO.builder()
                    .user(user)
                    .errorMessage(null)
                    .build();

        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return RegisterDTO.builder()
                    .user(null)
                    .errorMessage(ex.getMessage())
                    .build();
        }
    }

//    @Override
//    public String login(LoginDTO loginDTO) {
//        try {
//            Authentication authentication = manager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
//            );
//
////            return tokenService.getToken(authentication);
//            return "Logged in";
//
//        } catch (AuthenticationException ex) {
//            LOG.error(ex.getMessage());
//            return "Invalid login details";
//        }
//    }

    @Override
    public void saveVarificationToken(User user, String token) {
        VerificationToken verificationToken = VerificationToken.builder()
                .withUser(user)
                .token(token)
                .build();
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String verifyToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "Invalid";
        }
        User user = verificationToken.getUser();

        Calendar calendar = Calendar.getInstance();

        if (verificationToken.getExpiryDate().getTime()
        - calendar.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        user.setEnabled(true);
        userRepository.save(user);

        return "email verified";
    }

    @Override
    public String resetPassword(PasswordModel passwordModell) {
        Optional<User> optionalUser = userRepository.findByEmail(passwordModell.getEmail());
        User user = optionalUser.orElse(null);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                    .token(token)
                    .withUser(user)
                    .build();

            passwordResetTokenRepository.save(passwordResetToken);
            return token;
        }
        return null;
    }

    @Override
    public String verifyTokenAndChangePassword(String token, PasswordModel passwordModel) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            return "Invalid link";
        }
        User user = passwordResetToken.getUser();

        Calendar calendar = Calendar.getInstance();

        if (passwordResetToken.getExpiryDate().getTime()
                - calendar.getTime().getTime() <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired link";
        }

        if( user != null) {
            user.setPassword(passwordEncoder.encode(passwordModel.getNewPassword()));
            userRepository.save(user);

            return "Password updated!";
        }

        return "No such user";
    }
}
