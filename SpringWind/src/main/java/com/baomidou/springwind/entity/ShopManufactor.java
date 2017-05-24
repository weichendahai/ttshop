package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 厂商表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_manufactor")
public class ShopManufactor extends Model<ShopManufactor> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 厂商名字
     */
	private String manufactor;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getManufactor() {
		return manufactor;
	}

	public void setManufactor(String manufactor) {
		this.manufactor = manufactor;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
