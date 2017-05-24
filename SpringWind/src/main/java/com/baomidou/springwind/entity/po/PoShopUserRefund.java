package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopOrder;
import com.baomidou.springwind.entity.ShopOrderItem;
import com.baomidou.springwind.entity.ShopUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 用户退款记录表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_user_refund")
public class PoShopUserRefund extends Model<PoShopUserRefund> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("user_id")
	private Long userId;
    /**
     * 订单id
     */
	@TableField("order_id")
	private Long orderId;

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	/**
	 * 订单商品
	 */

	@TableField("order_item_id")
	private Integer orderItemId;
    /**
     * 商品id
     */
	@TableField("item_id")
	private Integer itemId;
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

	private ShopOrder shopOrder;

	private ShopItem shopItem;

	private ShopUser shopUser;

	private ShopOrderItem shopOrderItem;

	public ShopOrderItem getShopOrderItem() {
		return shopOrderItem;
	}

	public void setShopOrderItem(ShopOrderItem shopOrderItem) {
		this.shopOrderItem = shopOrderItem;
	}

	public ShopOrder getShopOrder() {
		return shopOrder;
	}

	public void setShopOrder(ShopOrder shopOrder) {
		this.shopOrder = shopOrder;
	}

	public ShopItem getShopItem() {
		return shopItem;
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	public ShopUser getShopUser() {
		return shopUser;
	}

	public void setShopUser(ShopUser shopUser) {
		this.shopUser = shopUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getCreateDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createDate);
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
