package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商品价格表，商品规格关联的价格
 * </p>
 *
 * @author Woody
 * @since 2017-05-04
 */
@TableName("shop_item_price")
public class ShopItemPrice extends Model<ShopItemPrice> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商品id
     */
	@TableField("item_id")
	private Integer itemId;
    /**
     * 规格选取路径,根据选取的路径,得到价格，路径的样式是0:0/1:2/4:0
     */
	@TableField("property_path")
	private String propertyPath;
    /**
     * 价格，单位分
     */
	private Integer price;
    /**
     * 显示使用的规格文字描述，与property_path对应，其的样式是红色/60ml/礼品包装
     */
	@TableField("property_path_name")
	private String propertyPathName;


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

	public String getPropertyPath() {
		return propertyPath;
	}

	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getPropertyPathName() {
		return propertyPathName;
	}

	public void setPropertyPathName(String propertyPathName) {
		this.propertyPathName = propertyPathName;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
