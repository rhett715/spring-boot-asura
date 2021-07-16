package org.rhett.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Author Rhett
 * @Date 2021/6/18
 * @Description
 */
public class RsaUtil {
    private static final int DEFAULT_KEY_SIZE = 2048;

    /**
     * 从文件中获取公钥
     * @param fileName 公钥保存路径，相对于classpath
     * @return 公钥
     * @throws Exception 异常
     */
    public static PublicKey getPublicKey(String fileName) throws Exception {
        byte[] bytes = readFile(fileName);
        return getPublicKey(bytes);
    }

    /**
     * 从文件中读取私钥
     * @param fileName 私钥保存路径，相对于classpath
     * @return 私钥
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKey(String fileName) throws Exception {
        byte[] bytes = readFile(fileName);
        return getPrivateKey(bytes);
    }

    /**
     * 获取公钥
     * @param bytes 公钥的字节形式
     * @return 公钥
     * @throws Exception 异常
     */
    public static PublicKey getPublicKey(byte[] bytes) throws Exception {
        byte[] decode = Base64.getDecoder().decode(bytes);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decode);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(keySpec);
    }

    /**
     * 获取私钥
     * @param bytes 私钥的字节形式
     * @return 私钥
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKey(byte[] bytes) throws Exception {
        byte[] decode = Base64.getDecoder().decode(bytes);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decode);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(keySpec);
    }

    /**
     * 根据密文，生存rsa公钥和私钥,并写入指定文件
     * @param publicKeyFileName 公钥文件路径
     * @param privateKeyFileName 私钥文件路径
     * @param secret 用于生成密钥的密文
     * @param keySize 密钥大小
     * @throws Exception 异常
     */
    public static void generateKey(String publicKeyFileName, String privateKeyFileName, String secret, int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        // 获取公钥并写出
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        publicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        writeFile(publicKeyFileName, publicKeyBytes);
        // 获取私钥并写出
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        privateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
        writeFile(privateKeyFileName, privateKeyBytes);
    }

    private static byte[] readFile(String fileName) throws IOException {
        return Files.readAllBytes(new File(fileName).toPath());
    }

    private static void writeFile(String fileName, byte[] bytes) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(file.toPath(), bytes);
    }
 }
