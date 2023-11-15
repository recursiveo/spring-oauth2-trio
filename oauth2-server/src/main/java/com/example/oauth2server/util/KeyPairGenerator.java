package com.example.oauth2server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class KeyPairGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(KeyPairGenerator.class);

    @Value("${rsa.public-key}")
    private Resource rsaPublicKeyFile;

    @Value("${rsa.private-key}")
    private Resource rsaPrivateKey;

    public RSAPublicKey getRsaPublicKey() {
        try {
            byte[] publicKeyBytes = rsaPublicKeyFile.getInputStream().readAllBytes();
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public RSAPrivateKey getRsaPrivateKey() {
        try {
            byte[] privateKeyBytes = rsaPrivateKey.getInputStream().readAllBytes();
            EncodedKeySpec privKeySpec =  new PKCS8EncodedKeySpec(privateKeyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);

        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
