package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.springwind.entity.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Server on 2017/3/29.
 */
public class POOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    @TableField("order_code")
    private String orderCode;
    /**
     * 订单状态，0表示代付款，1表示待收货，2表示待评价
     */
    @TableField("order_state")
    private Integer orderState;
    @TableField("express_code")
    private String expressCode;
    /**
     * 订单金额，单位分，注意100是一元钱，均是整数，显示时，除以100显示。
     */
    @TableField("order_total")
    private Integer orderTotal;
    /**
     * 运费，单位分，100是一元
     */
    private Integer freight;

    @TableField("create_date")
    private Date createDate;
    @TableField("modify_date")
    private Date modifyDate;

    /**
     * 收货联系人和地址
     */
    private ShopUserAddr shopUserAddr;
    /**
     *红包或者优惠券
     */
    private ShopUserPromo shopUserPromo;

    public ShopUserPromo getShopUserPromo() {
        return shopUserPromo;
    }

    public void setShopUserPromo(ShopUserPromo shopUserPromo) {
        this.shopUserPromo = shopUserPromo;
    }

    private List<ShopOrderItem> list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public List<ShopOrderItem> getList() {
        return list;
    }

    public void setList(List<ShopOrderItem> list) {
        this.list = list;
    }

    public ShopUserAddr getShopUserAddr() {
        return shopUserAddr;
    }

    public void setShopUserAddr(ShopUserAddr shopUserAddr) {
        this.shopUserAddr = shopUserAddr;
    }

}
