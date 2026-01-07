package com.ruoyi.receipts.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.receipts.mapper.CouponReceiptsMapper;
import com.ruoyi.receipts.domain.CouponReceipts;
import com.ruoyi.receipts.service.ICouponReceiptsService;

/**
 * 优惠券领取记录Service业务层处理
 * 
 * @author sun
 * @date 2025-12-15
 */
@Service
public class CouponReceiptsServiceImpl implements ICouponReceiptsService 
{
    @Autowired
    private CouponReceiptsMapper couponReceiptsMapper;

    /**
     * 查询优惠券领取记录
     * 
     * @param receiptId 优惠券领取记录主键
     * @return 优惠券领取记录
     */
    @Override
    public CouponReceipts selectCouponReceiptsByReceiptId(Long receiptId)
    {
        return couponReceiptsMapper.selectCouponReceiptsByReceiptId(receiptId);
    }

    /**
     * 查询优惠券领取记录列表
     * 
     * @param couponReceipts 优惠券领取记录
     * @return 优惠券领取记录
     */
    @Override
    public List<CouponReceipts> selectCouponReceiptsList(CouponReceipts couponReceipts)
    {
        return couponReceiptsMapper.selectCouponReceiptsList(couponReceipts);
    }

    /**
     * 新增优惠券领取记录
     * 
     * @param couponReceipts 优惠券领取记录
     * @return 结果
     */
    @Override
    public int insertCouponReceipts(CouponReceipts couponReceipts)
    {
        return couponReceiptsMapper.insertCouponReceipts(couponReceipts);
    }

    /**
     * 修改优惠券领取记录
     * 
     * @param couponReceipts 优惠券领取记录
     * @return 结果
     */
    @Override
    public int updateCouponReceipts(CouponReceipts couponReceipts)
    {
        return couponReceiptsMapper.updateCouponReceipts(couponReceipts);
    }

    /**
     * 批量删除优惠券领取记录
     * 
     * @param receiptIds 需要删除的优惠券领取记录主键
     * @return 结果
     */
    @Override
    public int deleteCouponReceiptsByReceiptIds(Long[] receiptIds)
    {
        return couponReceiptsMapper.deleteCouponReceiptsByReceiptIds(receiptIds);
    }

    /**
     * 删除优惠券领取记录信息
     * 
     * @param receiptId 优惠券领取记录主键
     * @return 结果
     */
    @Override
    public int deleteCouponReceiptsByReceiptId(Long receiptId)
    {
        return couponReceiptsMapper.deleteCouponReceiptsByReceiptId(receiptId);
    }
}
