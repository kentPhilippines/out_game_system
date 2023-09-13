package com.lt.win.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class HashUtil {

    private static final String SHA_256 = "SHA-256";
    private static final String SHA_1 = "SHA-1";

    public static String sha256(String input) {
        return hash(input, SHA_256);
    }

    public static String sha1(String input) {
        return hash(input, SHA_1);
    }

    private static String hash(String input, String hashAlgorithm) {
        if (input == null) {
            return null;
        }
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(hashAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("this can't happen", e);
        }
        byte[] hash = md.digest(input.getBytes());
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

}
