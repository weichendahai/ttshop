package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 订单商品关系表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_order_item_properties")
public class ShopOrderItemProperties extends Model<ShopOrderItemProperties> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * shop_order_item_properties表的id，用于区分订单中的商品
     */
	@TableField("order_item_id")
	private Long orderItemId;
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


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
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
