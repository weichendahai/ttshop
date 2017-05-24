package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopItemCategory;
import com.baomidou.springwind.entity.ShopItemProperties;
import com.baomidou.springwind.entity.ShopOrder;

import java.io.Serializable;

/**
 * <p>
 * 订单商品关系表
 * </p>
 *
 * @author Woody
 * @since 2017-04-06
 */
@TableName("shop_order_item_properties")
public class PoShopOrderItemProperties extends Model<PoShopOrderItemProperties> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单id
     */
	@TableField("order_id")
	private Long orderId;
    /**
     * 商品id
     */
	@TableField("item_id")
	private Integer itemId;
    /**
     * 属性的key，不同的值代表色号，型号，容量，款型等
     */
	@TableField("property_key")
	private Integer propertyKey;
    /**
     * 针对属性key的数值，例如红色，白色，蓝色等颜色，也可以是300ml，500ml等
     */
	@TableField("property_value")
	private Integer propertyValue;
    /**
     * 单价
     */
	private Integer price;
    /**
     * 数量
     */
	private Integer count;

	private ShopOrder shopOrder;

	private ShopItem shopItem;

	private ShopItemProperties shopItemProperties;

	private ShopItemCategory shopItemCategory;

	public ShopItemCategory getShopItemCategory() {
		return shopItemCategory;
	}

	public void setShopItemCategory(ShopItemCategory shopItemCategory) {
		this.shopItemCategory = shopItemCategory;
	}

	public ShopItemProperties getShopItemProperties() {
		return shopItemProperties;
	}

	public void setShopItemProperties(ShopItemProperties shopItemProperties) {
		this.shopItemProperties = shopItemProperties;
	}

	public ShopItem getShopItem() {
		return shopItem;
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	public ShopOrder getShopOrder() {
		return shopOrder;
	}

	public void setShopOrder(ShopOrder shopOrder) {
		this.shopOrder = shopOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(Integer propertyKey) {
		this.propertyKey = propertyKey;
	}

	public Integer getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Integer propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}



}
