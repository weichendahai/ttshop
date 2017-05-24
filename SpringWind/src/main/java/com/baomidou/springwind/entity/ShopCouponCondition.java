package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 优惠券使用条件表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_coupon_condition")
public class ShopCouponCondition extends Model<ShopCouponCondition> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("coupon_id")
	private Integer couponId;
    /**
     * 类型，例如限定商品，限定品牌，限定金额，限定地区
     */
	@TableField("condition_type")
	private Integer conditionType;
    /**
     * 条件的描述，例如满足999-50
     */
	@TableField("condition_desc")
	private String conditionDesc;
    /**
     * 第一个条件参数，例如满足999
     */
	@TableField("parameter_first")
	private Integer parameterFirst;
    /**
     * 第二个参数，表示满足第一个条件后，带来的结果，例如减50
     */
	@TableField("parameter_second")
	private Integer parameterSecond;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public Integer getConditionType() {
		return conditionType;
	}

	public void setConditionType(Integer conditionType) {
		this.conditionType = conditionType;
	}

	public String getConditionDesc() {
		return conditionDesc;
	}

	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}

	public Integer getParameterFirst() {
		return parameterFirst;
	}

	public void setParameterFirst(Integer parameterFirst) {
		this.parameterFirst = parameterFirst;
	}

	public Integer getParameterSecond() {
		return parameterSecond;
	}

	public void setParameterSecond(Integer parameterSecond) {
		this.parameterSecond = parameterSecond;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
