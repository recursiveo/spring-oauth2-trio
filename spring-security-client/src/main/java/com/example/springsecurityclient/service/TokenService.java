//package com.example.springsecurityclient.service;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.jwt.JwtClaimsSet;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.stream.Collectors;
//
//@Service
//public class TokenService {
//
//    private final JwtEncoder jwtEncoder;
//
//    public TokenService(JwtEncoder jwtEncoder) {
//        this.jwtEncoder = jwtEncoder;
//    }
//
//    public String getToken(Authentication auth) {
//        Instant instant = Instant.now();
//
//        String scope = auth.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "))
//                .toString();
//
//        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
//                .issuer("self")
//                .issuedAt(instant)
//                .expiresAt(instant.plus(1, ChronoUnit.HOURS))
//                .subject(auth.getName())
//                .claim("scope", scope)
//                .build();
//
//        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
//    }
//}
