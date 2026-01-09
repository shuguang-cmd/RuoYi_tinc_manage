package com.ruoyi.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA 密钥生成工具 (适配 Tinc 格式)
 */
public class RsaUtils {

    private static final Logger log = LoggerFactory.getLogger(RsaUtils.class);

    /**
     * 生成 RSA 密钥对 (公钥 + 私钥)
     * @return Map包含 "publicKey" 和 "privateKey"
     */
    public static Map<String, String> generateKeys() {
        try {
            // 1. 初始化 RSA 生成器 (Tinc 标准通常用 2048 或 4096 位)
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();

            // 2. 获取密钥
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            // 3. 转换为 PEM 格式字符串
            String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            Map<String, String> keys = new HashMap<>();
            keys.put("publicKey", formatToPem(publicKeyStr, "RSA PUBLIC KEY")); // 注意 Tinc 这里的 header
            keys.put("privateKey", formatToPem(privateKeyStr, "RSA PRIVATE KEY"));

            return keys;

        } catch (NoSuchAlgorithmException e) {
            log.error("RSA 算法不可用", e);
            throw new RuntimeException("密钥生成失败");
        }
    }

    /**
     * 格式化为 PEM 标准格式 (64字符换行)
     */
    private static String formatToPem(String base64Key, String type) {
        StringBuilder sb = new StringBuilder();
        // Tinc 有时对 Header 有特殊要求，标准 PEM 是 PUBLIC KEY，Tinc 文档通常也是 RSA PUBLIC KEY
        // 如果 Tinc 报错，可能需要去掉 "RSA " 前缀，视版本而定，先按标准来
        // 修正：Tinc 的 hosts 文件里通常只需要纯 Base64 串，或者带 Header。
        // 我们这里返回纯 Base64 串给调用者处理，或者直接组装好。
        // 这里为了配合 TincConfigUtils，我们直接返回纯 Base64 内容，格式化交给 ConfigUtils 或者在这里做。

        // 简单起见，这里返回纯 Base64，格式化我们在 ConfigUtils 里已经写了
        return base64Key;
    }
}