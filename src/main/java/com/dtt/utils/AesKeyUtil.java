package com.dtt.utils;

import java.nio.charset.StandardCharsets;

public class AesKeyUtil {

    // Build a 16-byte AES key from an arbitrary-length secret string
    public static byte[] buildKeyBytes(String secretKey) {
        byte[] keyBytes = new byte[16]; // AES-128
        byte[] secretBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(secretBytes, 0, keyBytes, 0,
                Math.min(keyBytes.length, secretBytes.length));
        return keyBytes;
    }
}
