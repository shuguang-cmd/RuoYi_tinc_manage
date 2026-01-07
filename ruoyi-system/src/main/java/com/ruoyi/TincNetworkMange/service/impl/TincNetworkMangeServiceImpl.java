package com.ruoyi.TincNetworkMange.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.TincNetworkMange.mapper.TincNetworkMangeMapper;
import com.ruoyi.TincNetworkMange.domain.TincNetworkMange;
import com.ruoyi.TincNetworkMange.service.ITincNetworkMangeService;

import com.ruoyi.manger.service.*;
import com.ruoyi.manger.domain.*;

/**
 * Tinc内网集群管理Service业务层处理
 * 
 * @author sun
 * @date 2025-12-20
 */
@Service
public class TincNetworkMangeServiceImpl implements ITincNetworkMangeService 
{
    @Autowired
    private TincNetworkMangeMapper tincNetworkMangeMapper;

    /**
     * 查询Tinc内网集群管理
     * 根据ID获取单个Tinc内网集群的详细配置信息
     * 用于编辑加载数据和查看详情
     * 
     * @param id Tinc内网集群管理主键
     * @return Tinc内网集群管理对象
     */
    @Override
    public TincNetworkMange selectTincNetworkMangeById(Long id)
    {
        return tincNetworkMangeMapper.selectTincNetworkMangeById(id);
    }

    /**
     * 查询Tinc内网集群管理列表
     * 根据查询条件获取Tinc内网集群管理列表数据
     * 为前端表格提供数据来源
     * 
     * @param tincNetworkMange Tinc内网集群管理查询条件
     * @return Tinc内网集群管理集合列表
     */
    @Override
    public List<TincNetworkMange> selectTincNetworkMangeList(TincNetworkMange tincNetworkMange)
    {
        return tincNetworkMangeMapper.selectTincNetworkMangeList(tincNetworkMange);
    }

    /**
     * 新增Tinc内网集群管理
     * 添加新的Tinc内网集群配置信息
     * 自动设置创建时间
     * 包括服务器选择、端口分配、网段配置等
     * 
     * @param tincNetworkMange Tinc内网集群管理信息
     * @return 操作结果（影响行数）
     */
    @Override
    public int insertTincNetworkMange(TincNetworkMange tincNetworkMange)
    {
        tincNetworkMange.setCreateTime(DateUtils.getNowDate());
        return tincNetworkMangeMapper.insertTincNetworkMange(tincNetworkMange);
    }

    /**
     * 修改Tinc内网集群管理
     * 更新已存在的Tinc内网集群配置信息
     * 可修改服务器、端口、网段等配置
     * 
     * @param tincNetworkMange Tinc内网集群管理信息
     * @return 操作结果（影响行数）
     */
    @Override
    public int updateTincNetworkMange(TincNetworkMange tincNetworkMange)
    {
        return tincNetworkMangeMapper.updateTincNetworkMange(tincNetworkMange);
    }

    /**
     * 批量删除Tinc内网集群管理
     * 根据ID数组批量删除Tinc内网集群配置信息
     * 
     * @param ids 需要删除的Tinc内网集群管理主键数组
     * @return 操作结果（影响行数）
     */
    @Override
    public int deleteTincNetworkMangeByIds(Long[] ids)
    {
        return tincNetworkMangeMapper.deleteTincNetworkMangeByIds(ids);
    }

    /**
     * 删除Tinc内网集群管理信息
     * 根据单个ID删除Tinc内网集群配置信息
     * 
     * @param id Tinc内网集群管理主键
     * @return 操作结果（影响行数）
     */
    @Override
    public int deleteTincNetworkMangeById(Long id)
    {
        return tincNetworkMangeMapper.deleteTincNetworkMangeById(id);
    }
    @Autowired
    private IMangeServerService mangeServerService;
    
    /**
     * 获取所有可用端口列表
     * 为前端端口选项框提供数据来源
     * 从服务器集群管理(MangeServer)中获取所有服务器的端口范围，并生成完整的端口列表
     * 
     * @return 可用端口列表
     */
    @Override
    public List<Long> getAvailablePorts(){
        List<Long> ports = new ArrayList<>();
        // 查询所有服务器集群信息
        List<MangeServer> serverList = mangeServerService.selectMangeServerList(new MangeServer());
        // 遍历每个服务器，生成其端口范围内的所有端口
        for(MangeServer server:serverList){
            Long startPort = server.getStartPost();
            Long endPort = server.getEndPost();
            if(startPort != null && endPort != null){
                // 生成从起始端口到结束端口的所有端口号
                for(Long port = startPort; port <= endPort; port++){
                    ports.add(port);
                }
            }
        }
        return ports;
    }
    
    /**
     * 获取所有可用网段列表
     * 为前端网段选项框提供数据来源
     * 从服务器集群管理(MangeServer)中获取所有服务器的网段范围
     * 
     * @return 可用网段列表
     */
    @Override
    public List<String> getAvailableSegments(){
        List<String> segments = new ArrayList<>();
        // 查询所有服务器集群信息
        List<MangeServer> serverList = mangeServerService.selectMangeServerList(new MangeServer());
        // 遍历每个服务器，获取其网段范围
        for(MangeServer server:serverList){
            String startInterat = server.getStartInterat();
            String endInterat  = server.getEndInterat();
            if(startInterat != null && endInterat != null){
                // 将起始网段和结束网段添加到列表中
                // 注意：这里只添加了起始和结束网段，实际前端会根据这些值生成完整的网段范围
                segments.add(startInterat);
                segments.add(endInterat);
            }
        }
        return segments;
    }
}
