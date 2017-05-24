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
 * 佣金提现表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_bonus_payment")
public class ShopBonusPayment extends Model<ShopBonusPayment> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 提现金额，单位分
     */
	private Integer total;
    /**
     * 申请时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 当前状态
     */
	private Integer state;
    /**
     * 支付时间
     */
	@TableField("payment_date")
	private Date paymentDate;


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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
