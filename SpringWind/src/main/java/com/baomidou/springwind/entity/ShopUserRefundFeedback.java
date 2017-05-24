package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_user_refund_feedback")
public class ShopUserRefundFeedback extends Model<ShopUserRefundFeedback> {

    private static final long serialVersionUID = 1L;
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * shop_user_refund表id
     */
	@TableField("user_refund_id")
	private Integer userRefundId;
    /**
     * 此消息创建时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 反馈信息
     */
	private String message;
    /**
     * 0表示客户反馈，1表示客服反馈，2表示库房反馈，3表示商城反馈，4客户经理反馈
     */
	private Integer from;
    /**
     * 附带的图片地址
     */
	@TableField("image_addr")
	private String imageAddr;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserRefundId() {
		return userRefundId;
	}

	public void setUserRefundId(Integer userRefundId) {
		this.userRefundId = userRefundId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public String getImageAddr() {
		return imageAddr;
	}

	public void setImageAddr(String imageAddr) {
		this.imageAddr = imageAddr;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
