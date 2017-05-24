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
 * 试用表
 * </p>
 *
 * @author Woody
 * @since 2017-04-30
 */
@TableName("shop_sample")
public class ShopSample extends Model<ShopSample> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商品id
     */
	@TableField("item_id")
	private Integer itemId;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 开始时间
     */
	@TableField("start_date")
	private Date startDate;
    /**
     * 结束时间
     */
	@TableField("end_date")
	private Date endDate;
    /**
     * 申请数量
     */
	@TableField("apply_count")
	private Integer applyCount;
    /**
     * 试用数量
     */
	@TableField("item_count")
	private Integer itemCount;
    /**
     * 试用图片地址
     */
	@TableField("sample_image_addr")
	private String sampleImageAddr;
    /**
     * 状态，0未开始，1，申请中，2，已结束，3已分配
     */
	private Integer state;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public String getSampleImageAddr() {
		return sampleImageAddr;
	}

	public void setSampleImageAddr(String sampleImageAddr) {
		this.sampleImageAddr = sampleImageAddr;
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
