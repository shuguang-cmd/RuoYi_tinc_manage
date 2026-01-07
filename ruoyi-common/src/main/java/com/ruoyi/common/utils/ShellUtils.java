package com.ruoyi.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Linux Shell 命令执行工具类
 */
public class ShellUtils {
    private static final Logger log = LoggerFactory.getLogger(ShellUtils.class);

    public static String runCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            // 记录日志，方便排查
            log.info("Executing Shell Command: {}", command);

            // 判断系统类型，如果是 Windows 开发环境，就假装执行成功（否则你会报错）
            if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
                log.warn("Windows environment detected. Skipping actual shell execution.");
                return "Mock Success in Windows";
            }

            // Linux 下真实执行
            ProcessBuilder builder = new ProcessBuilder("/bin/sh", "-c", command);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            // 设置超时时间，防止命令卡死 (10秒)
            boolean finished = process.waitFor(10, TimeUnit.SECONDS);

            if (finished) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                // 打印执行结果
                log.info("Command Output: {}", output.toString());
            } else {
                process.destroyForcibly();
                throw new RuntimeException("Shell command timed out: " + command);
            }

        } catch (Exception e) {
            log.error("Shell Execution Failed", e);
            throw new RuntimeException("Failed to execute command: " + command);
        }
        return output.toString();
    }
}