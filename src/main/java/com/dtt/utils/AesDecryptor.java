package com.dtt.utils;

import com.dtt.utils.AesKeyUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesDecryptor {

    private static final int GCM_TAG_LENGTH = 128;  // 16 bytes tag

    public static String decryptBase64(String base64CipherText, String secretKey) {
        try {
            byte[] encrypted = Base64.getDecoder().decode(base64CipherText);

            byte[] keyBytes = AesKeyUtil.buildKeyBytes(secretKey);
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

            // First 12 bytes = IV (recommended GCM IV length)
            byte[] iv = new byte[12];
            System.arraycopy(encrypted, 0, iv, 0, 12);

            // Remaining = ciphertext + tag
            byte[] cipherText = new byte[encrypted.length - 12];
            System.arraycopy(encrypted, 12, cipherText, 0, cipherText.length);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);

            byte[] plain = cipher.doFinal(cipherText);
            return new String(plain, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("AES-GCM decryption failed", e);
        }
    }
}
