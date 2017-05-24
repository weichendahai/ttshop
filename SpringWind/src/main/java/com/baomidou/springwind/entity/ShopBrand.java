package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_brand")
public class ShopBrand extends Model<ShopBrand> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 厂家id
     */
	@TableField("manufactor_id")
	private Integer manufactorId;
    /**
     * 品牌名称
     */
	@TableField("brand_name")
	private String brandName;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getManufactorId() {
		return manufactorId;
	}

	public void setManufactorId(Integer manufactorId) {
		this.manufactorId = manufactorId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
