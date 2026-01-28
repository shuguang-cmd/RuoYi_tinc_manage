package com.ruoyi.TincNetworkMange.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.RsaUtils;
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
    // 在 TincNetworkMangeServiceImpl.java 中替换原有的 insert 方法
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertTincNetworkMange(TincNetworkMange network)
    {
        // 1. 设置基础信息并入库
        network.setCreateTime(DateUtils.getNowDate());
        network.setNetworkStatus("正常运行中");
        int rows = tincNetworkMangeMapper.insertTincNetworkMange(network);

        // 2. [新增] 立即生成服务端文件到 D 盘
        try {
            String netName = network.getNetworkName();

            // =========================================================
            // 🔍 关键步骤：根据你选的“服务器名字”去查它的“真实IP”
            // =========================================================

            // 1. 获取你在下拉框里选的名字 (比如 "Tinc_Test")
            String selectedServerName = network.getServerName();
            // 注意：请确认你的实体类里字段名是 getAccessServer() 还是 getAccessServerName()

            if (selectedServerName == null || selectedServerName.isEmpty()) {
                throw new RuntimeException("请选择接入服务器！");
            }

            // 2. 去服务器表里查这个名字对应的记录
            MangeServer query = new MangeServer();
            query.setServerName(selectedServerName); // 假设 MangeServer 实体类用 setServerName 查询
            List<MangeServer> serverList = mangeServerService.selectMangeServerList(query);

            if (serverList == null || serverList.isEmpty()) {
                throw new RuntimeException("系统里找不到名为 [" + selectedServerName + "] 的服务器，请检查服务器集群管理！");
            }

            // 3. 拿到真实的公网 IP
            String realPublicIp = serverList.get(0).getServerIp();
            // =========================================================

            // A. 初始化目录
            TincConfigUtils.initNetworkEnv(netName);

            // B. 生成 4096位 密钥
            Map<String, String> keyMap = RsaUtils.generateKeys();

            // C. 生成配置 (server_master)
            TincConfigUtils.createTincConf(netName, "server_master", "");

            // D. 生成 Host 文件 (使用刚才查出来的 realPublicIp)
            // 这里的 subnet 是你填的网段 (比如 192.168.103) + .1/32
            String subnet = network.getSegment() + ".1/32";

            // 👇 这里填入真实的 IP
            TincConfigUtils.createHostFile(netName, "server_master", subnet, realPublicIp, keyMap.get("publicKey"));

            // E. 生成私钥
            TincConfigUtils.createPrivateKey(netName, keyMap.get("privateKey"));

        } catch (Exception e) {
            // 手动回滚：如果文件生成失败，把刚才插入数据库的那条记录也删了，保证数据一致性
            throw new RuntimeException("内网初始化失败: " + e.getMessage());
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