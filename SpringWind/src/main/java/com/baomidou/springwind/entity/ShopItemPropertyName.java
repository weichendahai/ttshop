package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商品规格维护表，此表用于维护规格，将规格
 * </p>
 *
 * @author Woody
 * @since 2017-05-02
 */
@TableName("shop_item_property_name")
public class ShopItemPropertyName extends Model<ShopItemPropertyName> {

    private static final long serialVersionUID = 1L;

    /**
     * 规格key
     */
	@TableId(value="property_key", type= IdType.AUTO)
	private Integer propertyKey;
    /**
     * 规格名称
     */
	@TableField("property_name")
	private String propertyName;


	public Integer getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(Integer propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	protected Serializable pkVal() {
		return this.propertyKey;
	}

}
