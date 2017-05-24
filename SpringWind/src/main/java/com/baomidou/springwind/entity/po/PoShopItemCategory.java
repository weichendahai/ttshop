package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.springwind.entity.ShopItemCategory;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品分类表，配合商品使用
 * </p>
 *
 * @author Woody
 * @since 2017-04-13
 */
@TableName("shop_item_category")
public class PoShopItemCategory extends Model<PoShopItemCategory> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 分类的级别
     */
	@TableField("category_level")
	private Integer categoryLevel;
    /**
     * 分类的名称
     */
	@TableField("category_name")
	private String categoryName;
	private Integer pid;

	private ShopItemCategory shopItemCategory;

	public ShopItemCategory getShopItemCategory() {
		return shopItemCategory;
	}

	public void setShopItemCategory(ShopItemCategory shopItemCategory) {
		this.shopItemCategory = shopItemCategory;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(Integer categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
