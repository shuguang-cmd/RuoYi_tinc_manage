package com.ruoyi.node_manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShellUtils; // 1. 引入 ShellUtils
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
    @Transactional(rollbackFor = Exception.class) // 3. 开启事务：任何异常都会导致数据库回滚
    public int insertTincNodeMange(TincNodeMange tincNodeMange)
    {
        // 1. 设置基础数据
        tincNodeMange.setCreateTime(DateUtils.getNowDate());

        // 2. 先入库 (确保数据格式正确，并且获取到自增ID)
        int rows = tincNodeMangeMapper.insertTincNodeMange(tincNodeMange);

        // 3. 执行 Linux 核心逻辑
        try {
            // 获取必要的参数 (假设实体类里有这些字段，你需要根据实际情况调整 getter 方法)
            String nodeName = tincNodeMange.getNodeName(); // 节点名
            // String netName = ...; // 如果前端只传了 netId，你需要先查询出 netName

            // --- 构造命令 ---
            // 这是一个示例命令，你需要替换成你真实的 Tinc 命令
            // 例如: "tinc -n [netName] generate-keys [nodeName]"
            // 这里我们先用 echo 测试一下流程是否通畅
            String command = String.format("echo 'Creating Tinc Node: %s' >> /tmp/tinc_debug.log", nodeName);

            // 4. 调用 ShellUtils
            // 如果命令执行失败 (exit code != 0) 或者超时，这里会抛出异常
            ShellUtils.runCommand(command);

        } catch (Exception e) {
            // 5. 异常捕获与回滚
            // 如果 Shell 失败了，我们需要抛出 RuntimeException，这样上面的 @Transactional 就会自动把数据库里刚插的那条记录删掉
            throw new RuntimeException("Tinc 系统底层执行失败: " + e.getMessage());
        }

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