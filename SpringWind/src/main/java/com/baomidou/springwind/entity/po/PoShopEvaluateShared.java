package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 点评的分享记录表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_evaluate_shared")
public class PoShopEvaluateShared extends Model<PoShopEvaluateShared> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 点评id，shop_item_evaluate表
     */
	@TableField("evaluate_id")
	private Long evaluateId;
    /**
     * 分享的用户id
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 分享时间
     */
	@TableField("create_date")
	private Date createDate;

	private ShopUser shopUser;

	private ShopItem shopItem;

	public ShopUser getShopUser() {
		return shopUser;
	}

	public void setShopUser(ShopUser shopUser) {
		this.shopUser = shopUser;
	}

	public ShopItem getShopItem() {
		return shopItem;
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCreateDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createDate);
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
