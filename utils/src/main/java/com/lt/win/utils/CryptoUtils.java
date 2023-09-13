package com.lt.win.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author: David
 * @date: 21/03/2020
 * @description: 本类为RSA加解密通用函数, 使用流程如下
 * 未提供PublicKey PrivateKey的情况下使用generatorKey生成公私钥对
 * 已生成公私钥对(Base64码字符串的前提下)使用如下:
 * 1. 使用getPrivateKey,getPublicKey获取公私钥对
 * 2. base64Str2PublicKey base64Str2PrivateKey将Base64字符串还原PublicKey,PrivateKey
 * 3. 使用PublicKey,PrivateKey进行加解密以及签名、认证操作
 */
@Slf4j
public class CryptoUtils {
    /**
     * 私钥
     */
    PrivateKey sk;
    /**
     * 公钥
     */
    PublicKey pk;
    /**
     * 密钥工厂
     */
    KeyFactory kf;

    public CryptoUtils() throws NoSuchAlgorithmException {
        this.kf = KeyFactory.getInstance("RSA");
    }

//    public static void main(String[] args) throws UnsupportedEncodingException, GeneralSecurityException {
//        CryptoUtils crypto = new CryptoUtils();
//        String base64PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCh6L4wA1hkXWsZjV/dn4YJBqR/hPWyk+G8qVgs7+MR0vPjOfcCEGLaZWkcOFbiR9ghXnyDwLZv61hSABzuszwgypKljD0SdNn4LxUMF/U7om5FyHXTR/nzvqzGjf88vbnSFiiM/WTSwwR7I48c4yUsJ2DcCVrm65Be229DJH8aDwIDAQAB";
//        String base64PrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKHovjADWGRdaxmNX92fhgkGpH+E9bKT4bypWCzv4xHS8+M59wIQYtplaRw4VuJH2CFefIPAtm/rWFIAHO6zPCDKkqWMPRJ02fgvFQwX9TuibkXIddNH+fO+rMaN/zy9udIWKIz9ZNLDBHsjjxzjJSwnYNwJWubrkF7bb0MkfxoPAgMBAAECgYAODV00XZX2YAVDmnmPiqDfd6wfRTLt5Nfav/ffLpLOoTh5qDY//5wUyGhvApTH3uRrQQAdj0RGQm7P7yKTtQqqymKRPZRx6Eie9/pDuIBjMq1HNzXCTn1VTzs1QNCbPYbEk0qs2yTnzudURqeDnank3P9W70oBRBvJ1olJpIfDQQJBAOHM6O6/1sWUM53v7CI0+iahUnC7zfgORjhLL7YQQPLxOlylPiY+5nIbBb9WmhpCmoKfJ35J0k5GDbDHds0LaF0CQQC3kEkHz1lbRABPMwAmiKQAR6UCK17GZTSzXW1Bhr6qpe7D/QAWFCXA7aLZbbJwbFtTMd2mv67Nq5/qEJEH0PVbAkBlriiWmrzUbFByJsBnBwQ+sRa2vVubAJKOdw0dJ2SYAhkN7zt1bY3IhHKqHw12FdgOSi02qXeS1+kCWlAdNa3hAkEArcKqLvdrWYY8WLwpV1CfFhi7rRsK4vioc2Vr58w6LmVWaDXU4BGAT2ljbzh04hDgPheE/rSSLuRYQwQdlW2K4wJABphZ/M6GPij+L358ZriMne8gM+pf7SLGTYB1Jm7j5J79WHqjZYFaC+bOzQz2jJre/nxfMnxWWgOVcQAxycXWhw==";
//        PrivateKey sk = crypto.base64Str2PrivateKey(base64PrivateKey);
//        PublicKey pk = crypto.base64Str2PublicKey(base64PublicKey);
//
//        ///*********************************** 签名及认证 START *********************************/
//        //// 待签名的消息
//        //byte[] message = "Hello, I am Bob!".getBytes(StandardCharsets.UTF_8);
//        //// 用私钥签名
//        //byte[] signed = crypto.sign(message);
//        //System.out.println(String.format("signature: %x", new BigInteger(1, signed)));
//        //// 用公钥验证
//        //boolean valid = crypto.verify(message, signed);
//        //System.out.println("valid? " + valid);
//        ///*********************************** 签名及认证 END ***********************************/
//
//        /*********************************** 加密及解密 START *********************************/
//        // 明文:
//        //byte[] plain = "8d4646eb2d7067126eb08adb0672f7bb".getBytes("UTF-8");
//        //byte[] encrypted = crypto.encrypt(plain);
//        // 用Alice的私钥解密:
//        String aabb = "QN1blnQV9l4Ayc80GqCWovyUFzPubHmpxZKCmVo/GUxt8NZCUzF9AKbxKi/+1deLFN1PJlTe8rMmtEvaAh+WSFedd6HuNMP2qUroAKCduF90FWlLqKN9fL9aJEwUQ72oauSTU9mkfoUuErxfWn/uiC89+TE6xIkK/GhD82GcFZM=";
//        byte[] aabb11 = Base64.getDecoder().decode(aabb);
//        byte[] decrypted = crypto.decrypt(aabb11);
//        //String encrypt11 = Base64.getEncoder().encodeToString(encrypted);
//        //System.out.println(encrypt11);
//        System.out.println(new String(decrypted, "UTF-8"));
//
//        /*********************************** 加密及解密 END ***********************************/
//    }

    /**
     * @desc: 生成RSA公私钥对
     * @params: []
     * @return: void
     * @author: David
     * @date: 21/03/2020
     */
    public void generateRsaKey() throws NoSuchAlgorithmException {
        // 生成公钥／私钥对:
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair kp = kpGen.generateKeyPair();
        this.sk = kp.getPrivate();
        this.pk = kp.getPublic();
    }

    /**
     * @desc: 获取公钥
     * @params: []
     * @return: byte[]
     * @author: David
     * @date: 21/03/2020
     */
    public PublicKey getPublicKey() {
        return this.pk;
    }

    /**
     * @desc: 获取私钥
     * @params: []
     * @return: java.security.PrivateKey
     * @author: David
     * @date: 21/03/2020
     */
    public PrivateKey getPrivateKey() {
        return this.sk;
    }

    /**
     * @desc: 私钥转Base64字符串
     * @params: [privateKey]
     * @return: java.lang.String
     * @author: David
     * @date: 21/03/2020
     */
    public String privateKey2Base64Str(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * @desc: 共钥转Base64字符串
     * @params: [privateKey]
     * @return: java.lang.String
     * @author: David
     * @date: 21/03/2020
     */
    public String publicKey2Base64Str(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * @desc: Base64字符串转PublicKey
     * @params: []
     * @return: void
     * @author: David
     * @date: 21/03/2020
     */
    public PublicKey base64Str2PublicKey(String base64str) throws InvalidKeySpecException {
        this.pk = kf.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(base64str)));
        return this.pk;
    }


    /**
     * @return
     * @desc: Base64字符串转PrivateKey
     * @params: []
     * @author: David
     * @date: 21/03/2020
     */
    public PrivateKey base64Str2PrivateKey(String base64str) throws InvalidKeySpecException {
        this.sk = kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64str)));
        return this.sk;
    }

    /**
     * @desc: 用公钥加密
     * @params: [message]
     * @return: byte[]
     * @author: David
     * @date: 21/03/2020
     */
    public byte[] encrypt(byte[] message) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, this.pk);
        return cipher.doFinal(message);
    }

    /**
     * @desc: 用私钥解密
     * @params: [input]
     * @return: byte[]
     * @author: David
     * @date: 21/03/2020
     */
    public byte[] decrypt(byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, this.sk);
        return cipher.doFinal(input);
    }

    /**
     * @desc: 签名 - 私钥加密
     * @params: [input]
     * @return: byte[]
     * @author: David
     * @date: 21/03/2020
     */
    public byte[] sign(byte[] input) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        // 用私钥签名:
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initSign(sk);
        s.update(input);
        return s.sign();
    }

    /**
     * @desc: 公钥验证
     * @params: [sign]
     * @return: boolean
     * @author: David
     * @date: 21/03/2020
     */
    public boolean verify(byte[] message, byte[] signed) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature v = Signature.getInstance("SHA1withRSA");
        v.initVerify(pk);
        v.update(message);
        return v.verify(signed);
    }

    /**
     * md5加密
     *
     * @param text
     * @return
     */
    public static String md5(String text) {
        try {
            log.info("md5加密前" + text);
            String md5 = DigestUtils.md5DigestAsHex(text.getBytes());
            log.info("md5加密后" + md5);
            return md5;
        } catch (Exception e) {
            log.error("md5加密失败！");
        }
        return "";
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String s = "cur=HKD/\\\\\\\\/password=123456/\\\\\\\\/actype=1/\\\\\\\\/method=ca/\\\\\\\\/cagent=TE410/\\\\\\\\/loginname=william0002123456";
        md5(s);
    }
}

