//package com.example.springsecurityclient.utils;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.security.*;
//
//public class RsaKeyGen {
//    public static void generateKeys() throws NoSuchAlgorithmException, IOException {
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(2048);
//
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//        PrivateKey privateKey = keyPair.getPrivate();
//        PublicKey publicKey = keyPair.getPublic();
//
//        try (FileOutputStream fout = new FileOutputStream("key.priv")) {
//            fout.write(privateKey.getEncoded());
//        }
//
//        try (FileOutputStream fout = new FileOutputStream("key.pub")) {
//            fout.write(publicKey.getEncoded());
//        }
//
//        System.out.println("Public key format: " + publicKey.getFormat());
//        System.out.println("Private Key format: " + privateKey.getFormat());
//    }
//
//    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
//        generateKeys();
//    }
//}
