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
@TableName("shop_order_item")
public class ShopOrderItem extends Model<ShopOrderItem> {

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
	@TableField("price")
	private Integer price;
    /**
     * 数量
     */
	@TableField("count")
	private Integer count;
    /**
     * 状态，0表示正常，1表示已经退货
     */
	@TableField("state")
	private Integer state;

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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
