package com.ruoyi.TincNetworkMange.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShellUtils;      // 必须引入我们写的 Shell 工具
import com.ruoyi.common.utils.TincConfigUtils; // 必须引入我们写的 Config 工具
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 引入事务控制
import com.ruoyi.TincNetworkMange.mapper.TincNetworkMangeMapper;
import com.ruoyi.TincNetworkMange.domain.TincNetworkMange;
import com.ruoyi.TincNetworkMange.service.ITincNetworkMangeService;

import com.ruoyi.manger.service.*;
import com.ruoyi.manger.domain.*;

/**
 * Tinc内网集群管理Service业务层处理
 * @author sun
 */
@Service
public class TincNetworkMangeServiceImpl implements ITincNetworkMangeService
{
    @Autowired
    private TincNetworkMangeMapper tincNetworkMangeMapper;

    @Autowired
    private IMangeServerService mangeServerService;

    // ... selectTincNetworkMangeById 保持不变 ...
    @Override
    public TincNetworkMange selectTincNetworkMangeById(Long id)
    {
        return tincNetworkMangeMapper.selectTincNetworkMangeById(id);
    }

    // ... selectTincNetworkMangeList 保持不变 ...
    @Override
    public List<TincNetworkMange> selectTincNetworkMangeList(TincNetworkMange tincNetworkMange)
    {
        return tincNetworkMangeMapper.selectTincNetworkMangeList(tincNetworkMange);
    }

    /**
     * 新增Tinc内网集群管理 (核心改造)
     * 对应 PHP Netmanagement::add
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 开启事务：任何报错都会回滚数据库
    public int insertTincNetworkMange(TincNetworkMange network)
    {
        // 1. 业务校验：检查端口和网段是否被占用
        // (需要在 Mapper 中补充这两个查询方法，稍后我会给你 SQL)
        if (tincNetworkMangeMapper.checkPortUnique(network.getPort()) > 0) {
            throw new RuntimeException("操作失败：端口 " + network.getPort() + " 已被占用");
        }
        if (tincNetworkMangeMapper.checkSegmentUnique(network.getSegment()) > 0) {
            throw new RuntimeException("操作失败：网段 " + network.getSegment() + " 已被占用");
        }

        // 2. 设置基础数据
        network.setCreateTime(DateUtils.getNowDate());
        network.setNetworkStatus("正常运行中"); // 默认状态

        // 3. 先入库 (获取 ID)
        int rows = tincNetworkMangeMapper.insertTincNetworkMange(network);

        // 4. 执行 Linux 底层操作
        try {
            String netName = network.getNetworkName();

            // --- NEW: 生成真实的 RSA 密钥 ---
            Map<String, String> keyMap = com.ruoyi.common.utils.RsaUtils.generateKeys();
            String publicKey = keyMap.get("publicKey");
            String privateKey = keyMap.get("privateKey");
            // -----------------------------

            // A. 生成 tinc.conf (保持不变)
            TincConfigUtils.createTincConf(netName, "server_master", "");

            // B. 生成服务端 Host 文件 (填入真实的 publicKey)
            // 注意：这里需要把生成的公钥传进去
            TincConfigUtils.createHostFile(netName, "server_master", network.getSegment(), "", publicKey);

            // C. 保存私钥到本地 (rsa_key.priv)
            // 我们需要在 TincConfigUtils 里补一个生成私钥的方法，或者直接在这里写
            // 简单起见，我们假设 TincConfigUtils 增加了一个 createPrivateKey 方法
            TincConfigUtils.createPrivateKey(netName, privateKey);

            // D. 启动服务 (保持不变)
            String startCmd = "systemctl start tinc@" + netName;
            ShellUtils.runCommand(startCmd);

        } catch (Exception e) {
            // ... 异常处理保持不变 ...
        }

        return rows;
    }

    // ... update, delete 方法保持不变 ...
    @Override
    public int updateTincNetworkMange(TincNetworkMange tincNetworkMange) {
        return tincNetworkMangeMapper.updateTincNetworkMange(tincNetworkMange);
    }

    @Override
    public int deleteTincNetworkMangeByIds(Long[] ids) {
        return tincNetworkMangeMapper.deleteTincNetworkMangeByIds(ids);
    }

    @Override
    public int deleteTincNetworkMangeById(Long id) {
        return tincNetworkMangeMapper.deleteTincNetworkMangeById(id);
    }

    // ... getAvailablePorts, getAvailableSegments 保持不变 ...
    @Override
    public List<Long> getAvailablePorts(){
        List<Long> ports = new ArrayList<>();
        List<MangeServer> serverList = mangeServerService.selectMangeServerList(new MangeServer());
        for(MangeServer server:serverList){
            Long startPort = server.getStartPost();
            Long endPort = server.getEndPost();
            if(startPort != null && endPort != null){
                for(Long port = startPort; port <= endPort; port++){
                    ports.add(port);
                }
            }
        }
        return ports;
    }

    @Override
    public List<String> getAvailableSegments(){
        List<String> segments = new ArrayList<>();
        List<MangeServer> serverList = mangeServerService.selectMangeServerList(new MangeServer());
        for(MangeServer server:serverList){
            String startInterat = server.getStartInterat();
            String endInterat  = server.getEndInterat();
            if(startInterat != null && endInterat != null){
                segments.add(startInterat);
                segments.add(endInterat);
            }
        }
        return segments;
    }
}