package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商品用户分布表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_item_user_distribution")
public class ShopItemUserDistribution extends Model<ShopItemUserDistribution> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商品id
     */
	@TableField("item_id")
	private Integer itemId;
    /**
     * 分布类型
     */
	private Integer type;
    /**
     * 分布key,如果type是年龄分布,�
     */
	private Integer sub;
    /**
     * 分布数量
     */
	private Integer count;
	@TableField("distribution_type_des")
	private String distributionTypeDes;
	@TableField("distribution_sub_desc")
	private String distributionSubDesc;


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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSub() {
		return sub;
	}

	public void setSub(Integer sub) {
		this.sub = sub;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDistributionTypeDes() {
		return distributionTypeDes;
	}

	public void setDistributionTypeDes(String distributionTypeDes) {
		this.distributionTypeDes = distributionTypeDes;
	}

	public String getDistributionSubDesc() {
		return distributionSubDesc;
	}

	public void setDistributionSubDesc(String distributionSubDesc) {
		this.distributionSubDesc = distributionSubDesc;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
