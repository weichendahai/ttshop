package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopItemEvaluate;
import com.baomidou.springwind.entity.ShopOrder;
import com.baomidou.springwind.entity.ShopUser;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 点评佣金表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_evaluate_bonus")
public class PoShopEvaluateBonus extends Model<PoShopEvaluateBonus> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 点评id
     */
	@TableField("evaluate_id")
	private Long evaluateId;
    /**
     * 订单id
     */
	@TableField("order_id")
	private Long orderId;
    /**
     * 用户id,冗余信息
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 商品id,冗余信息
     */
	@TableField("item_id")
	private Integer itemId;
    /**
     * 产生时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 奖金
     */
	private Integer bonus;

	private ShopOrder shopOrder;

	private ShopItem shopItem;

	private ShopUser shopUser;

	private ShopItemEvaluate shopItemEvaluate;

	public ShopItemEvaluate getShopItemEvaluate() {
		return shopItemEvaluate;
	}

	public void setShopItemEvaluate(ShopItemEvaluate shopItemEvaluate) {
		this.shopItemEvaluate = shopItemEvaluate;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getBonus() {
		return bonus;
	}

	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
