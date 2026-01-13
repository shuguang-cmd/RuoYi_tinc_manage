package com.ruoyi.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Tinc 专用 Zip 压缩工具
 */
public class ZipUtils {

    private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * 打包客户端配置文件
     * @param sourceFiles 要打包的文件列表 (File对象)
     * @param zipPath 压缩包输出路径 (例如 D:/tinc/temp/client_01.zip)
     */
    public static void createZip(List<File> sourceFiles, String zipPath) {
        File zipFile = new File(zipPath);
        // 确保父目录存在
        if (zipFile.getParentFile() != null && !zipFile.getParentFile().exists()) {
            zipFile.getParentFile().mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (File srcFile : sourceFiles) {
                if (!srcFile.exists()) {
                    continue;
                }
                addToZip(srcFile, zos);
            }
            log.info("ZIP打包成功: {}", zipPath);

        } catch (IOException e) {
            log.error("ZIP打包失败", e);
            throw new RuntimeException("生成压缩包失败");
        }
    }

    private static void addToZip(File file, ZipOutputStream zos) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        // 核心逻辑：保持 hosts/ 文件夹结构
        // 如果文件名是 server_master 或 client_node，我们强制把它塞进 hosts 文件夹里
        String entryName = file.getName();
        if (file.getParent().endsWith("hosts")) {
            entryName = "hosts/" + file.getName();
        }

        ZipEntry zipEntry = new ZipEntry(entryName);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }
}