package com.ruoyi.node_manage.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.RsaUtils;         // 必须引入
import com.ruoyi.common.utils.TincConfigUtils;  // 必须引入
import com.ruoyi.common.utils.ZipUtils;         // 必须引入
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.node_manage.mapper.TincNodeMangeMapper;
import com.ruoyi.node_manage.domain.TincNodeMange;
import com.ruoyi.node_manage.service.ITincNodeMangeService;

/**
 * Tinc节点集群管理Service业务层处理
 */
@Service
public class TincNodeMangeServiceImpl implements ITincNodeMangeService
{
    private static final Logger log = LoggerFactory.getLogger(TincNodeMangeServiceImpl.class);

    @Autowired
    private TincNodeMangeMapper tincNodeMangeMapper;

    // 查询方法保持不变...
    @Override
    public TincNodeMange selectTincNodeMangeById(Long id) {
        return tincNodeMangeMapper.selectTincNodeMangeById(id);
    }

    @Override
    public List<TincNodeMange> selectTincNodeMangeList(TincNodeMange tincNodeMange) {
        return tincNodeMangeMapper.selectTincNodeMangeList(tincNodeMange);
    }

    /**
     * 新增Tinc节点集群管理 (核心逻辑)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertTincNodeMange(TincNodeMange tincNodeMange)
    {
        // 1. 设置基础数据
        tincNodeMange.setCreateTime(DateUtils.getNowDate());

        // 2. 插入数据库 (获取 ID)
        int rows = tincNodeMangeMapper.insertTincNodeMange(tincNodeMange);

        // =================================================================
        // 3. 执行核心逻辑：生成密钥、配置文件并打包
        // =================================================================
        try {
            // 获取业务参数
            String netName = tincNodeMange.getNetworkName();
            String nodeName = tincNodeMange.getNodeName();

            // 容错处理：确保 IP 带掩码 (默认客户端是 /32)
            String rawIp = tincNodeMange.getnetworkIp(); // 注意你的 getter 命名可能是 getNetworkIp
            String nodeIp = rawIp.contains("/") ? rawIp : rawIp + "/32";

            // 调用下方的私有方法生成 Zip 包
            String zipPath = generateClientPackage(netName, nodeName, nodeIp);

            // 4. 将生成的 Zip 路径更新回数据库 (可选，方便前端下载)
            // 假设你的实体类有个 downloadUrl 字段，或者你直接打印日志
            // tincNodeMange.setDownloadUrl(zipPath);
            // tincNodeMangeMapper.updateTincNodeMange(tincNodeMange);

            log.info("节点 [{}] 创建成功，安装包路径: {}", nodeName, zipPath);

        } catch (Exception e) {
            log.error("节点创建失败，触发回滚", e);
            throw new RuntimeException("Tinc 节点部署失败: " + e.getMessage());
        }

        return rows;
    }

    /**
     * 私有核心方法：生成客户端专属安装包
     */
    private String generateClientPackage(String netName, String nodeName, String nodeIp) {

        // 1. 定义临时生成目录 (D:/tinc/netName/clients/nodeName)
        String tempDir = netName + "/clients/" + nodeName;

        // 2. 生成新的密钥对
        Map<String, String> keyMap = RsaUtils.generateKeys();
        String clientPubKey = keyMap.get("publicKey");
        String clientPrivKey = keyMap.get("privateKey");

        // ---------------------------------------------------------
        // A. 在临时目录生成客户端文件
        // ---------------------------------------------------------
        // A.1 生成 rsa_key.priv
        TincConfigUtils.createPrivateKey(tempDir, clientPrivKey);

        // A.2 生成 tinc.conf (ConnectTo 指向 server_master)
        TincConfigUtils.createTincConf(tempDir, nodeName, "server_master");

        // A.3 生成客户端自己的 host 文件 (注意是 nodeIp)
        TincConfigUtils.createHostFile(tempDir, nodeName, nodeIp, clientPubKey);

        // ---------------------------------------------------------
        // B. 把服务器的公钥复制过来
        // ---------------------------------------------------------
        try {
            // 读取服务器原本的 hosts/server_master
            String serverHostContent = TincConfigUtils.readHostFile(netName, "server_master");

            // 写入到临时目录
            String baseDir = TincConfigUtils.getBasePath();
            File tempServerHost = new File(baseDir + "/" + tempDir + "/hosts/server_master");
            if (!tempServerHost.getParentFile().exists()) {
                tempServerHost.getParentFile().mkdirs();
            }
            Files.write(tempServerHost.toPath(), serverHostContent.getBytes());

        } catch (Exception e) {
            throw new RuntimeException("获取服务器公钥失败，请检查服务端是否已初始化！");
        }

        // ---------------------------------------------------------
        // C. 注册客户端到服务器 (让服务器认识新节点)
        // ---------------------------------------------------------
        // 这步很关键：把客户端公钥写入服务器真正的 hosts 目录
        TincConfigUtils.createHostFile(netName, nodeName, nodeIp, clientPubKey);

        // ---------------------------------------------------------
        // D. 打包 ZIP
        // ---------------------------------------------------------
        try {
            String fullTempPath = TincConfigUtils.getBasePath() + "/" + tempDir;

            List<File> fileList = new ArrayList<>();
            fileList.add(new File(fullTempPath + "/tinc.conf"));
            fileList.add(new File(fullTempPath + "/rsa_key.priv"));
            fileList.add(new File(fullTempPath + "/hosts/" + nodeName));
            fileList.add(new File(fullTempPath + "/hosts/server_master"));

            // 目标路径：D:/tinc/zips/netName_nodeName.zip
            String zipName = netName + "_" + nodeName + ".zip";
            String zipPath = TincConfigUtils.getBasePath() + "/zips/" + zipName;

            ZipUtils.createZip(fileList, zipPath);

            return zipPath;

        } catch (Exception e) {
            throw new RuntimeException("打包 ZIP 失败: " + e.getMessage());
        }
    }

    // update, delete 等其他方法保持不变...
    @Override
    public int updateTincNodeMange(TincNodeMange tincNodeMange) {
        return tincNodeMangeMapper.updateTincNodeMange(tincNodeMange);
    }

    @Override
    public int deleteTincNodeMangeByIds(Long[] ids) {
        return tincNodeMangeMapper.deleteTincNodeMangeByIds(ids);
    }

    @Override
    public int deleteTincNodeMangeById(Long id) {
        return tincNodeMangeMapper.deleteTincNodeMangeById(id);
    }
}