package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.springwind.entity.ShopUser;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author Woody
 * @since 2017-04-06
 */
@TableName("shop_order")
public class PoShopOrder extends Model<PoShopOrder> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	@TableField("order_code")
	private String orderCode;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
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
     * 订单的收货地址id，关联用户地址表的id
     */
	@TableField("order_addr_id")
	private Integer orderAddrId;
    /**
     * 运费，单位分，100是一元
     */
	private Integer freight;
    /**
     * 使用的优惠券id，shop_user_promo表的id
     */
	@TableField("user_coupon_id")
	private Integer userCouponId;
    /**
     * 使用的红包id，shop_user_promo表的id
     */
	@TableField("user_gift_card_id")
	private Integer userGiftCardId;

	@TableField("create_date")
	private Date createDate;

	@TableField("modify_date")
	private Date modifyDate;

	private ShopUser shopUser;

	public ShopUser getShopUser() {
		return shopUser;
	}

	public void setShopUser(ShopUser shopUser) {
		this.shopUser = shopUser;
	}

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Integer getOrderAddrId() {
		return orderAddrId;
	}

	public void setOrderAddrId(Integer orderAddrId) {
		this.orderAddrId = orderAddrId;
	}

	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	public Integer getUserCouponId() {
		return userCouponId;
	}

	public void setUserCouponId(Integer userCouponId) {
		this.userCouponId = userCouponId;
	}

	public Integer getUserGiftCardId() {
		return userGiftCardId;
	}

	public void setUserGiftCardId(Integer userGiftCardId) {
		this.userGiftCardId = userGiftCardId;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
