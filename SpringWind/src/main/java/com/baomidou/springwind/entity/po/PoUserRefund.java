package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopOrder;
import com.baomidou.springwind.entity.ShopUserAddr;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Server on 2017/4/2.
 */
public class PoUserRefund implements Serializable {
    private Long id;

    private String orderCode;
    private  String nickname;
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    /*private ShopUserAddr shopUserAddr;*/
    /*private String contact;
    private String expressCode;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }*/
/**
     * 订单id
     */
    /*private ShopOrder shopOrder;*/
    /**
     * 商品id
     */
    /*private ShopItem shopItem;*/
    /**
     * 申请时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 作废掉,使用refund_type
     */
    private Integer reason;
    /**
     * 具体描述
     */
    private String info;
    /**
     * 图片地址
     */
    @TableField("image_addr")
    private String imageAddr;
    /**
     * 退货状态
     */
    @TableField("refund_state")
    private Integer refundState;
    /**
     * 退货类型
     */
    @TableField("refund_type")
    private Integer refundType;
    /**
     * 退货的金额
     */
    @TableField("refund_charge")
    private Integer refundCharge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImageAddr() {
        return imageAddr;
    }

    public void setImageAddr(String imageAddr) {
        this.imageAddr = imageAddr;
    }

    public Integer getRefundState() {
        return refundState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public Integer getRefundCharge() {
        return refundCharge;
    }

    public void setRefundCharge(Integer refundCharge) {
        this.refundCharge = refundCharge;
    }
}
