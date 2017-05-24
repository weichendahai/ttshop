package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商品属性表，型号，颜色，容量等
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_item_properties")
public class ShopItemProperties extends Model<ShopItemProperties> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商品id
     */
	@TableField("item_id")
	private Integer itemId;
    /**
     * 属性的key，不同的值代表色号，型号，容量，款型等
     */
	@TableField("property_key")
	private Integer propertyKey;
    /**
     * 针对属性key的数值，例如红色，白色，蓝色等颜色，也可以是300ml，500ml等
     */
	@TableField("property_value")
	private Integer propertyValue;
	@TableField("property_desc")
	private String propertyDesc;


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

	public Integer getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(Integer propertyKey) {
		this.propertyKey = propertyKey;
	}

	public Integer getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Integer propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPropertyDesc() {
		return propertyDesc;
	}

	public void setPropertyDesc(String propertyDesc) {
		this.propertyDesc = propertyDesc;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
