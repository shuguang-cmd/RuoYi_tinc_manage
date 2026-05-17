package com.ruoyi.common.utils;

import org.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA 密钥生成工具 (BouncyCastle 纯血 PKCS#1 版 - 绝对兼容 Tinc)
 * @author Sun & Gemini
 */
public class RsaUtils {

    private static final Logger log = LoggerFactory.getLogger(RsaUtils.class);

    public static Map<String, String> generateKeys() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(4096);
            KeyPair pair = keyGen.generateKeyPair();

            Map<String, String> keys = new HashMap<>();

            // 1. 生成纯正的 PKCS#1 私钥
            StringWriter privWriter = new StringWriter();
            try (JcaPEMWriter pemWriter = new JcaPEMWriter(privWriter)) {
                pemWriter.writeObject(pair.getPrivate());
            }
            keys.put("privateKey", privWriter.toString().replace("\r\n", "\n"));

            // 2. 🚨 核心终极修复：拒绝换皮！强行剥离信封，生成纯正的 PKCS#1 公钥
            java.security.interfaces.RSAPublicKey rsaPub = (java.security.interfaces.RSAPublicKey) pair.getPublic();

            // 使用 BouncyCastle 的底层 ASN.1 对象，手动拼装最古老的 RSA 结构
            RSAPublicKey pkcs1PubKey = new RSAPublicKey(rsaPub.getModulus(), rsaPub.getPublicExponent());

            StringWriter pubWriter = new StringWriter();
            try (PemWriter pemWriter = new PemWriter(pubWriter)) {
                // 明确写入 RSA PUBLIC KEY 表头，并塞入纯血的 PKCS#1 字节流
                pemWriter.writeObject(new PemObject("RSA PUBLIC KEY", pkcs1PubKey.getEncoded()));
            }
            keys.put("publicKey", pubWriter.toString().replace("\r\n", "\n"));

            return keys;

        } catch (Exception e) {
            log.error("RSA 算法不可用", e);
            throw new RuntimeException("密钥生成失败: " + e.getMessage());
        }
    }
}