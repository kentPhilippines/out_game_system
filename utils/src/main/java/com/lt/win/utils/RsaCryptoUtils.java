package com.lt.win.utils;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
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
public class RsaCryptoUtils {
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

    /**
     * 默认生成RSA密钥工厂
     *
     * @throws NoSuchAlgorithmException 异常
     */
    public RsaCryptoUtils() throws NoSuchAlgorithmException {
        this.kf = KeyFactory.getInstance("RSA");
    }

    public static void main(String[] args) throws GeneralSecurityException {
        RsaCryptoUtils rsaCryptoUtils = new RsaCryptoUtils();

        // 生成RSA公私钥对
        rsaCryptoUtils.generateRsaKey();

        // 获取公私钥对
        PublicKey publicKey = rsaCryptoUtils.getPublicKey();
        PrivateKey privateKey = rsaCryptoUtils.getPrivateKey();

        // 获取公私钥字符串
        String sPubKey = rsaCryptoUtils.publicKey2Base64Str(publicKey);
        log.info("publicKey:" + sPubKey);
        String sPriKey = rsaCryptoUtils.privateKey2Base64Str(privateKey);
        log.info("privateKey:" + sPriKey);

        // *********************************** 签名及认证 START *********************************
        // 待签名的消息
        String messsage = "Hello, I am Bob!";
        // 用私钥签名
        byte[] signed = rsaCryptoUtils.sign(messsage.getBytes(StandardCharsets.UTF_8));
        // 用公钥验证
        boolean valid = rsaCryptoUtils.verify(messsage.getBytes(StandardCharsets.UTF_8), signed);
        log.info("valid? " + valid);
        // *********************************** 签名及认证 END ***********************************

        // *********************************** 加密及解密 START *********************************
        // 明文:
        String strMessage = "hello, world2020";
        byte[] encrypted = rsaCryptoUtils.encrypt(strMessage.getBytes(StandardCharsets.UTF_8));
        log.info("hello, world2020加密后:" + rsaCryptoUtils.byteArr2Base64(encrypted));

        byte[] decrypted = rsaCryptoUtils.decrypt(encrypted);
        log.info("解密后" + new String(decrypted));
        // *********************************** 加密及解密 END ***********************************
    }

    /**
     * 初始化加密算法
     *
     * @return 加密
     */
    public Cipher initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance("RSA");
    }

    /**
     * 生成RSA公私钥对
     *
     * @author David
     * @date 03/06/2020
     * @throw NoSuchAlgorithmException  异常
     */
    private void generateRsaKey() throws NoSuchAlgorithmException {
        // 生成公钥／私钥对:
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(2048);
        KeyPair kp = kpGen.generateKeyPair();
        this.sk = kp.getPrivate();
        this.pk = kp.getPublic();
    }

    /**
     * 获取公钥
     *
     * @return 公钥
     */
    public PublicKey getPublicKey() {
        return this.pk;
    }

    /**
     * 获取私钥
     *
     * @return 私钥类型公钥
     */
    public PrivateKey getPrivateKey() {
        return this.sk;
    }

    /**
     * 私钥转字符串
     *
     * @param privateKey 私钥
     * @return 私钥字符串表现形式
     */
    public String privateKey2Base64Str(@NotNull PrivateKey privateKey) {
        return byteArr2Base64(privateKey.getEncoded());
    }

    /**
     * PublicKey 转Base64字符串
     *
     * @param publicKey 公钥
     * @return 公钥字符串形式
     */
    public String publicKey2Base64Str(@NotNull PublicKey publicKey) {
        return byteArr2Base64(publicKey.getEncoded());
    }

    /**
     * 字符串转PublicKey
     *
     * @param publicKey 字符串转64公钥
     * @return 公钥类型
     */
    public PublicKey base64Str2PublicKey(String publicKey) throws InvalidKeySpecException {
        this.pk = kf.generatePublic(new X509EncodedKeySpec(base642byteArr(publicKey)));
        return this.pk;
    }

    /**
     * 字符串转PrivateKey
     *
     * @param privateKey base64字符串转RSA私钥
     * @return RSA私钥
     */
    public PrivateKey base64Str2PrivateKey(String privateKey) throws InvalidKeySpecException {
        this.sk = kf.generatePrivate(new PKCS8EncodedKeySpec(base642byteArr(privateKey)));
        return this.sk;
    }

    /**
     * 公钥加密
     *
     * @param message 加密明文
     * @return 加密密文
     */
    public byte[] encrypt(byte[] message) throws GeneralSecurityException {
        Cipher cipher = initCipher();
        cipher.init(Cipher.ENCRYPT_MODE, this.pk);
        return cipher.doFinal(message);
    }

    /**
     * 私钥解密
     *
     * @param input 需要解密的密文
     * @return 解密后的明文
     */
    public byte[] decrypt(byte[] input) throws GeneralSecurityException {
        Cipher cipher = initCipher();
        cipher.init(Cipher.DECRYPT_MODE, this.sk);
        return cipher.doFinal(input);
    }

    /**
     * 签名 - 私钥加密
     *
     * @param input 明文
     * @return 签名后的秘文
     */
    public byte[] sign(byte[] input) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initSign(sk);
        s.update(input);
        return s.sign();
    }

    /**
     * 公钥验证RSA秘文是否合法
     *
     * @param message RSA加密前的明文
     * @param signed  加密后的密文
     * @return 验证成功: true-成功 false-失败
     */
    public boolean verify(byte[] message, byte[] signed) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature v = Signature.getInstance("SHA1withRSA");
        v.initVerify(pk);
        v.update(message);
        return v.verify(signed);
    }

    /**
     * byte[]类型 转 Base64字符串
     *
     * @param bytes byte[]
     * @return Base64字符串
     */
    public String byteArr2Base64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base64字符串 转 byte[]类型
     *
     * @param string 要转换成byte[]的字符串
     * @return byte[]
     */
    public byte[] base642byteArr(String string) {
        return Base64.getDecoder().decode(string);
    }
}
