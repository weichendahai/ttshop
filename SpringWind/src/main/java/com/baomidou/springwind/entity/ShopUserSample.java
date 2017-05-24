package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户试用订单表，
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_user_sample")
public class ShopUserSample extends Model<ShopUserSample> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 试用id,shop_sample表id
     */
	@TableField("sample_id")
	private Integer sampleId;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 0：申请中
1：已中签
2：未中签
3：已结束 4立即申请
     */
	private Integer state;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSampleId() {
		return sampleId;
	}

	public void setSampleId(Integer sampleId) {
		this.sampleId = sampleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
