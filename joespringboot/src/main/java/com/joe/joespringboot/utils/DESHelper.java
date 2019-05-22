package com.joe.joespringboot.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESHelper {
    private static final String CIPHER_NAME = "DES";
    private static final String CIPHER_MODE = "CBC";
    private static final String CIPHER_PADDING = "PKCS5Padding";
    private static final String CIPHER_OPTIONS = CIPHER_NAME + "/" + CIPHER_MODE + "/" + CIPHER_PADDING;
    private static final byte[] CIPHER_IV = new byte[]{
            (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};

    public static byte[] encrypt(byte[] plaintext, byte[] key) throws Exception {

        IvParameterSpec iv       = new IvParameterSpec(CIPHER_IV);
        SecretKeySpec   skeySpec = new SecretKeySpec(key, CIPHER_NAME);

        Cipher cipher = Cipher.getInstance(CIPHER_OPTIONS);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        return cipher.doFinal(plaintext);
    }

    public static byte[] decrypt(byte[] ciphertext, byte[] key) throws Exception {

        IvParameterSpec iv = new IvParameterSpec(CIPHER_IV);
        SecretKeySpec skeySpec = new SecretKeySpec(key, CIPHER_NAME);

        Cipher cipher = Cipher.getInstance(CIPHER_OPTIONS);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        return cipher.doFinal(ciphertext);
    }
}
