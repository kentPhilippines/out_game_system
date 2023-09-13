package com.lt.win.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * Aes 加密类
 *
 * @author david
 */
@Slf4j
public class AesUtils {
    private AesUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * AES-CBC-128 加密
     *
     * @param data 明文
     * @param key  key
     * @param iv   iv
     * @return 密文
     */
    public static String encrypt(String data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            int plainTextLength = dataBytes.length;
            if (plainTextLength % blockSize != 0) {
                plainTextLength = plainTextLength + (blockSize - plainTextLength % blockSize);
            }
            byte[] plaintext = new byte[plainTextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64.encodeBase64URLSafeString(encrypted);
        } catch (Exception e) {
            log.info(e.getMessage());
            return "";
        }
    }

    /**
     * AES-CBC-128 加密
     *
     * @param data 密文
     * @param key  key
     * @param iv   iv
     * @return 明文
     */
    public static @NotNull String decrypt(String data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"),
                    new IvParameterSpec(iv.getBytes()));
            return new String(cipher.doFinal(Base64.decodeBase64(data))).trim();
        } catch (Exception e) {
            log.info(e.getMessage());
            return "";
        }
    }

    public static void main(String[] args) throws Exception {
        var aa = "SampleData";
        String encrypt = encrypt(aa, "d76d84fa7a477c18", "08275a8153d3e16e");
        log.info("EncryptData : " + encrypt);

        String decrypt = decrypt(encrypt, "d76d84fa7a477c18", "08275a8153d3e16e");
        log.info("DecryptData : " + decrypt);

        var dd = "zV5WtGLECDxAkYVl2hFVP-0HLBdQSl3lTd1gVTUpqhkj5Ro-Qqbcy_BYEKJb8GNuZjsy6SUGr6VMotn4mi6_Fijk6UlsrxqoiKwyPVWOzaZI3cn0wv8VcEcPagheqgNIy0SkrpCoVgckujtQYOCB5A";
        log.info("Dd: " + decrypt(dd, "d76d84fa7a477c18", "08275a8153d3e16e"));


    }
}

