package com.ruoyi.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.node_manage.domain.TincNodeMange;
import com.ruoyi.node_manage.service.ITincNodeMangeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Tinc节点集群管理Controller
 * 
 * @author sun
 * @date 2025-12-22
 */
@RestController
@RequestMapping("/node_mange/node_mange")
public class TincNodeMangeController extends BaseController
{
    @Autowired
    private ITincNodeMangeService tincNodeMangeService;

    /**
     * 查询Tinc节点集群管理列表
     */
    @PreAuthorize("@ss.hasPermi('node_mange:node_mange:list')")
    @GetMapping("/list")
    public TableDataInfo list(TincNodeMange tincNodeMange)
    {
        startPage();
        List<TincNodeMange> list = tincNodeMangeService.selectTincNodeMangeList(tincNodeMange);
        return getDataTable(list);
    }

    /**
     * 导出Tinc节点集群管理列表
     */
    @PreAuthorize("@ss.hasPermi('node_mange:node_mange:export')")
    @Log(title = "Tinc节点集群管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TincNodeMange tincNodeMange)
    {
        List<TincNodeMange> list = tincNodeMangeService.selectTincNodeMangeList(tincNodeMange);
        ExcelUtil<TincNodeMange> util = new ExcelUtil<TincNodeMange>(TincNodeMange.class);
        util.exportExcel(response, list, "Tinc节点集群管理数据");
    }

    /**
     * 获取Tinc节点集群管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('node_mange:node_mange:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tincNodeMangeService.selectTincNodeMangeById(id));
    }

    /**
     * 新增Tinc节点集群管理
     */
    @PreAuthorize("@ss.hasPermi('node_mange:node_mange:add')")
    @Log(title = "Tinc节点集群管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TincNodeMange tincNodeMange)
    {
        return toAjax(tincNodeMangeService.insertTincNodeMange(tincNodeMange));
    }

    /**
     * 修改Tinc节点集群管理
     */
    @PreAuthorize("@ss.hasPermi('node_mange:node_mange:edit')")
    @Log(title = "Tinc节点集群管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TincNodeMange tincNodeMange)
    {
        return toAjax(tincNodeMangeService.updateTincNodeMange(tincNodeMange));
    }

    /**
     * 删除Tinc节点集群管理
     */
    @PreAuthorize("@ss.hasPermi('node_mange:node_mange:remove')")
    @Log(title = "Tinc节点集群管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tincNodeMangeService.deleteTincNodeMangeByIds(ids));
    }
}
