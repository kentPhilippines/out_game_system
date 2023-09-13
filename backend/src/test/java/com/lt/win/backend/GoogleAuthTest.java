package com.lt.win.backend;

import com.lt.win.utils.DateUtils;
import com.lt.win.utils.GoogleAuthenticator;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @Author : Wells
 * @Date : 2021-01-01 2:51 下午
 * @Description : xx
 */

//@SpringBootTest
public class GoogleAuthTest {

    @Test
    void getRandomSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        String secretKey = base32.encodeToString(bytes);
        var secret = secretKey.toUpperCase();
        System.out.println("secret==" + secret);
    }

    @Test
    @SneakyThrows
    void getTOTPCode() {
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10000);
            String normalizedBase32Key = "1".replace(" ", "").toUpperCase();
            Base32 base32 = new Base32();
            byte[] bytes = base32.decode(normalizedBase32Key);
            String hexKey = Hex.encodeHexString(bytes);
            long time = (System.currentTimeMillis() / 1000) / 30;
            String hexTime = Long.toHexString(time);
            var code = GoogleAuthenticator.generateTOTP(hexKey, hexTime, "6");
            System.out.println("date==" + DateUtils.yyyyMMddHHmmss(DateUtils.getCurrentTime()) + "code==" + code);
        }
    }


    @Test
    void braCode() {
        String randomSecretKey = GoogleAuthenticator.getRandomSecretKey();
        String googleAuthenticatorBarCode = GoogleAuthenticator.getGoogleAuthenticatorBarCode(randomSecretKey, "wells001", "https://www.lrshuai.top");
        System.out.println("googleAuthenticatorBarCode=" + googleAuthenticatorBarCode);
    }

    private static int verify_code(byte[] key, long t)
            throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        // We're using a long because Java hasn't got unsigned int.
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            // We are dealing with signed bytes:
            // we just keep the first byte.
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }
}
