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
import java.util.List;

/**
 * <p>
 * 订单商品关系表
 * </p>
 *
 * @author Woody
 * @since 2017-04-24
 */
@TableName("shop_order_item")
public class PoShopOrderItem extends Model<PoShopOrderItem> {

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
     * 单价
     */
	private Integer price;
    /**
     * 数量
     */
	private Integer count;

	private ShopItem shopItem;

	public ShopItem getShopItem() {
		return shopItem;
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	private ShopItemCategory shopItemCategory;

	private ShopItemProperties shopItemProperties;

	public ShopItemProperties getShopItemProperties() {
		return shopItemProperties;
	}

	public void setShopItemProperties(ShopItemProperties shopItemProperties) {
		this.shopItemProperties = shopItemProperties;
	}

	public ShopItemCategory getShopItemCategory() {
		return shopItemCategory;
	}

	public void setShopItemCategory(ShopItemCategory shopItemCategory) {
		this.shopItemCategory = shopItemCategory;
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
