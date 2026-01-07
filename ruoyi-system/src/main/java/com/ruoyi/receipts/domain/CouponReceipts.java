package com.ruoyi.receipts.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 优惠券领取记录对象 coupon_receipts
 * 
 * @author sun
 * @date 2025-12-15
 */
public class CouponReceipts extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 领取记录ID */
    private Long receiptId;

    /** 顾客ID */
    @Excel(name = "顾客ID")
    private String customerId;

    /** 优惠券ID */
    @Excel(name = "优惠券ID")
    private String couponId;

    /** 领取时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "领取时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveTime;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setReceiptId(Long receiptId) 
    {
        this.receiptId = receiptId;
    }

    public Long getReceiptId() 
    {
        return receiptId;
    }

    public void setCustomerId(String customerId) 
    {
        this.customerId = customerId;
    }

    public String getCustomerId() 
    {
        return customerId;
    }

    public void setCouponId(String couponId) 
    {
        this.couponId = couponId;
    }

    public String getCouponId() 
    {
        return couponId;
    }

    public void setReceiveTime(Date receiveTime) 
    {
        this.receiveTime = receiveTime;
    }

    public Date getReceiveTime() 
    {
        return receiveTime;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("receiptId", getReceiptId())
            .append("customerId", getCustomerId())
            .append("couponId", getCouponId())
            .append("receiveTime", getReceiveTime())
            .append("status", getStatus())
            .toString();
    }
}
