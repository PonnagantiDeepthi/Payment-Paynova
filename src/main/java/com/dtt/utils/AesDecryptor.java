/**
 * @author Vijay Kumar
 */
package com.dtt.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesDecryptor {

    public static String decryptBase64(String base64CipherText, String secretKey) {
        try {
            byte[] cipherBytes = Base64.getDecoder().decode(base64CipherText);
            byte[] keyBytes = AesKeyUtil.buildKeyBytes(secretKey);

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec iv = new IvParameterSpec(keyBytes); // same as C# sample

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

            byte[] plain = cipher.doFinal(cipherBytes);
            return new String(plain, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed", e);
        }
    }
}
