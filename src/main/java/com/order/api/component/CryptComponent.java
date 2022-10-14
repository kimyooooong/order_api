package com.order.api.component;

import lombok.Getter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

@Component
public class CryptComponent {

    public static String alg = "AES/CBC/PKCS5Padding";
    private final String key = "AES_256_SECRET_KEY_PROJECT_TEST1";
    private final String iv = key.substring(0, 16); // 16byte
    private final String algorithm = "AES";

    @Getter
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    public String getOrderKey(int keyLength) {
        Random rnd=new Random();
        StringBuffer buf=new StringBuffer();
        for(int i=1;i<=keyLength;i++) {
            if(rnd.nextBoolean())
                buf.append((char)(rnd.nextInt(26)+65));   // 0~25(26개) + 65
            else
                buf.append(rnd.nextInt(10));
        }
        return buf.toString();
    }

}