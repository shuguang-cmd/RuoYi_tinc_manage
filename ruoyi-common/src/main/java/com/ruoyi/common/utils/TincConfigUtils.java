package com.ruoyi.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Tinc 配置文件生成工具
 */
public class TincConfigUtils {

    private static final Logger log = LoggerFactory.getLogger(TincConfigUtils.class);

    private static final String BASE_PATH = System.getProperty("os.name").toLowerCase().startsWith("win")
            ? "D:/tinc"
            : "/etc/tinc";

    /**
     * 生成 tinc.conf
     */
    public static void createTincConf(String netName, String nodeName, String connectToNode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name = ").append(nodeName).append("\n");
        sb.append("Interface = tinc").append("\n");
        sb.append("ConnectTo = ").append(connectToNode).append("\n");
        sb.append("Mode = switch").append("\n");

        writeToFile(netName, "tinc.conf", sb.toString());
    }

    // ==========================================
    // 核心修改：利用重载解决参数冲突
    // ==========================================

    /**
     * 版本 A (兼容旧代码): 只有 4 个参数
     * 给普通的 Client 节点使用（不需要公网IP）
     */
    public static void createHostFile(String netName, String nodeName, String subnet, String publicKey) {
        // 自动调用版本 B，把 publicIp 传为 null
        createHostFile(netName, nodeName, subnet, null, publicKey);
    }

    /**
     * 版本 B (新功能): 有 5 个参数
     * 给 Server 节点使用（需要写入 Address 公网IP）
     */
    public static void createHostFile(String netName, String nodeName, String subnet, String publicIp, String publicKey) {
        StringBuilder sb = new StringBuilder();

        // 只有当 publicIp 不为空时，才写入 Address 字段
        if (publicIp != null && !publicIp.isEmpty()) {
            sb.append("Address = ").append(publicIp).append("\n");
        }

        sb.append("Subnet = ").append(subnet).append("\n");
        sb.append("\n");
        sb.append("-----BEGIN RSA PUBLIC KEY-----\n");
        sb.append(publicKey).append("\n");
        sb.append("-----END RSA PUBLIC KEY-----\n");

        writeToFile(netName, "hosts/" + nodeName, sb.toString());
    }

    // ==========================================

    /**
     * 生成私钥文件 (rsa_key.priv)
     */
    public static void createPrivateKey(String netName, String privateKey) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----BEGIN RSA PRIVATE KEY-----\n");
        sb.append(privateKey).append("\n");
        sb.append("-----END RSA PRIVATE KEY-----\n");

        writeToFile(netName, "rsa_key.priv", sb.toString());
    }

    /**
     * 内部写文件方法
     */
    private static void writeToFile(String netName, String fileName, String content) {
        String fullPath = BASE_PATH + "/" + netName + "/" + fileName;
        File file = new File(fullPath);

        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }
            log.info("配置文件生成成功: {}", fullPath);
        } catch (IOException e) {
            log.error("无法写入文件: {}", fullPath, e);
            throw new RuntimeException("生成配置失败: " + e.getMessage());
        }
    }

    // ==========================================
    // 新增：读取文件功能 (为了获取 Server 的公钥)
    // ==========================================

    /**
     * 读取指定节点的 hosts 文件内容
     * @param netName 内网名称
     * @param nodeName 节点名称 (例如 server_master)
     * @return 文件内容字符串
     */
    public static String readHostFile(String netName, String nodeName) {
        String fullPath = BASE_PATH + "/" + netName + "/hosts/" + nodeName;
        File file = new File(fullPath);

        if (!file.exists()) {
            log.error("文件不存在: {}", fullPath);
            throw new RuntimeException("找不到服务器公钥文件，请检查是否已初始化服务端！");
        }

        try {
            // 简单暴力的读取方式 (Java 7+)
            return new String(java.nio.file.Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            log.error("读取文件失败: {}", fullPath, e);
            throw new RuntimeException("读取配置文件失败");
        }
    }

    /**
     * 获取基础路径 (给 Zip 工具用)
     */
    public static String getBasePath() {
        return BASE_PATH;
    }
}