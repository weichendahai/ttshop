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
 * 
 * </p>
 *
 * @author Woody
 * @since 2017-05-01
 */
@TableName("shop_user_filter_record")
public class ShopUserFilterRecord extends Model<ShopUserFilterRecord> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * shop_user_filter_scheme表的id
     */
	@TableField("scheme_id")
	private Integer schemeId;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 方案应用时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 0表示商品试用筛选，1表示优惠券筛选，2表示红包筛选
     */
	private Integer reason;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getReason() {
		return reason;
	}

	public void setReason(Integer reason) {
		this.reason = reason;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
