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
 * 用户拥有的优惠券红包表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_user_promo")
public class ShopUserPromo extends Model<ShopUserPromo> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 优惠券或者红包id，对应的是优惠券表id，或者红包表id
     */
	@TableField("promo_id")
	private Integer promoId;
    /**
     * 发放时间
     */
	@TableField("grant_date")
	private Date grantDate;
    /**
     * 0表示优惠券，1表示红包
     */
	@TableField("promo_type")
	private Integer promoType;
    /**
     * 状态，未用，已用等
     */
	private Integer state;
    /**
     * 过期时间
     */
	@TableField("end_date")
	private Date endDate;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPromoId() {
		return promoId;
	}

	public void setPromoId(Integer promoId) {
		this.promoId = promoId;
	}

	public Date getGrantDate() {
		return grantDate;
	}

	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
	}

	public Integer getPromoType() {
		return promoType;
	}

	public void setPromoType(Integer promoType) {
		this.promoType = promoType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
