package com.ruoyi.node_manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShellUtils; // 1. 引入 ShellUtils
import com.ruoyi.common.utils.TincConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 2. 引入事务注解
import com.ruoyi.node_manage.mapper.TincNodeMangeMapper;
import com.ruoyi.node_manage.domain.TincNodeMange;
import com.ruoyi.node_manage.service.ITincNodeMangeService;

/**
 * Tinc节点集群管理Service业务层处理
 * * @author sun
 * @date 2025-12-22
 */
@Service
public class TincNodeMangeServiceImpl implements ITincNodeMangeService
{
    @Autowired
    private TincNodeMangeMapper tincNodeMangeMapper;

    // TODO: 如果你需要根据 netId 查询内网名称，这里需要注入 TincNetworkMangeMapper
    // @Autowired
    // private TincNetworkMangeMapper tincNetworkMangeMapper;

    /**
     * 查询Tinc节点集群管理
     */
    @Override
    public TincNodeMange selectTincNodeMangeById(Long id)
    {
        return tincNodeMangeMapper.selectTincNodeMangeById(id);
    }

    /**
     * 查询Tinc节点集群管理列表
     */
    @Override
    public List<TincNodeMange> selectTincNodeMangeList(TincNodeMange tincNodeMange)
    {
        return tincNodeMangeMapper.selectTincNodeMangeList(tincNodeMange);
    }

    /**
     * 新增Tinc节点集群管理 (核心修改部分)
     * * @param tincNodeMange Tinc节点集群管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertTincNodeMange(TincNodeMange tincNodeMange)
    {
        // 1. 基础数据入库
        tincNodeMange.setCreateTime(DateUtils.getNowDate());
        int rows = tincNodeMangeMapper.insertTincNodeMange(tincNodeMange);

        // =================================================================
        // 2. 核心：生成 Tinc 配置文件 (开始)
        // =================================================================
        try {
            // 从实体类获取必要参数
            String netName = tincNodeMange.getNetworkName(); // 例如 "home_vpn"
            String nodeName = tincNodeMange.getNodeName();   // 例如 "notebook"
            String serverName = tincNodeMange.getServerName(); // 例如 "aliyun" (ConnectTo 的目标)
            String ip = tincNodeMange.getnetworkIp();        // 例如 "192.172.1.2/32"

            // A. 生成 tinc.conf
            // 对应截图：D:\tinc\home_vpn\tinc.conf
            TincConfigUtils.createTincConf(netName, nodeName, serverName);

            // B. 生成 hosts 文件
            // 对应截图：D:\tinc\home_vpn\hosts\notebook
            // 暂时给一个假的公钥用于测试，后续我们会用 Shell 生成真的
            String mockPublicKey = "MIIBCgKCAQEAw...";
            TincConfigUtils.createHostFile(netName, nodeName, ip, mockPublicKey);

            // C. 执行 Shell 命令 (可选)
            // ShellUtils.runCommand("tincd -n " + netName + " -K");

        } catch (Exception e) {
            e.printStackTrace();
            // 抛出异常，触发事务回滚 (数据库里刚才插的记录会被删掉)
            throw new RuntimeException("Tinc 文件生成失败，操作已回滚");
        }
        // =================================================================
        // 核心逻辑结束
        // =================================================================

        return rows;
    }

    /**
     * 修改Tinc节点集群管理
     */
    @Override
    public int updateTincNodeMange(TincNodeMange tincNodeMange)
    {
        return tincNodeMangeMapper.updateTincNodeMange(tincNodeMange);
    }

    /**
     * 批量删除Tinc节点集群管理
     */
    @Override
    public int deleteTincNodeMangeByIds(Long[] ids)
    {
        return tincNodeMangeMapper.deleteTincNodeMangeByIds(ids);
    }

    /**
     * 删除Tinc节点集群管理信息
     */
    @Override
    public int deleteTincNodeMangeById(Long id)
    {
        return tincNodeMangeMapper.deleteTincNodeMangeById(id);
    }
}