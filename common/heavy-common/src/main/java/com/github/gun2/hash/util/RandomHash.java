package com.github.gun2.hash.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomHash {
    private static final MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static String getRandomHash(int cycle){
        byte[] hash = UUID.randomUUID().toString().getBytes();
        // x번 해싱을 반복
        for (int i = 0; i < cycle; i++) {
            hash = digest.digest(hash);
        }
        return bytesToHex(hash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
