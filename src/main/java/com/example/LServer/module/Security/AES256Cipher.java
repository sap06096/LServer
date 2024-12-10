package com.example.LServer.module.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

@Component
public class AES256Cipher {

    public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    @Value("${LCore.crypto.key}")
    String secret;

    public String AES_Encode(String str) throws Exception {
        if (!StringUtils.hasLength(str)) {
            return null;
        }

        try {
            byte[] textBytes = str.getBytes("UTF-8");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(secret.getBytes("UTF-8"), "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(cipher.doFinal(textBytes));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("암호화를 실패했습니다.\n문의 부탁드립니다.");
        }
    }

    public String AES_Decode(String str) throws Exception {
        if (!StringUtils.hasLength(str)) {
            return null;
        }

        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] textBytes = decoder.decode(str);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            return new String(cipher.doFinal(textBytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("복호화를 실패했습니다.\n문의 부탁드립니다.");
        }
    }
}
