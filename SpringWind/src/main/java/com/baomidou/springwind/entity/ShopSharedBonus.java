package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户分享奖励表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_shared_bonus")
public class ShopSharedBonus extends Model<ShopSharedBonus> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 分享id
     */
	@TableField("shared_id")
	private Long sharedId;
    /**
     * 订单id
     */
	@TableField("order_id")
	private Long orderId;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 商品id
     */
	@TableField("item_id")
	private Long itemId;
    /**
     * 产生时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 佣金
     */
	private Integer bonus;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSharedId() {
		return sharedId;
	}

	public void setSharedId(Long sharedId) {
		this.sharedId = sharedId;
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
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
