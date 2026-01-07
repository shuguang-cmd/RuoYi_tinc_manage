package com.ruoyi.receipts.mapper;

import java.util.List;
import com.ruoyi.receipts.domain.CouponReceipts;

/**
 * 优惠券领取记录Mapper接口
 * 
 * @author sun
 * @date 2025-12-15
 */
public interface CouponReceiptsMapper 
{
    /**
     * 查询优惠券领取记录
     * 
     * @param receiptId 优惠券领取记录主键
     * @return 优惠券领取记录
     */
    public CouponReceipts selectCouponReceiptsByReceiptId(Long receiptId);

    /**
     * 查询优惠券领取记录列表
     * 
     * @param couponReceipts 优惠券领取记录
     * @return 优惠券领取记录集合
     */
    public List<CouponReceipts> selectCouponReceiptsList(CouponReceipts couponReceipts);

    /**
     * 新增优惠券领取记录
     * 
     * @param couponReceipts 优惠券领取记录
     * @return 结果
     */
    public int insertCouponReceipts(CouponReceipts couponReceipts);

    /**
     * 修改优惠券领取记录
     * 
     * @param couponReceipts 优惠券领取记录
     * @return 结果
     */
    public int updateCouponReceipts(CouponReceipts couponReceipts);

    /**
     * 删除优惠券领取记录
     * 
     * @param receiptId 优惠券领取记录主键
     * @return 结果
     */
    public int deleteCouponReceiptsByReceiptId(Long receiptId);

    /**
     * 批量删除优惠券领取记录
     * 
     * @param receiptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCouponReceiptsByReceiptIds(Long[] receiptIds);
}
