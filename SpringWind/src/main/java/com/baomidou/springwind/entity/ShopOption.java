package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商城参数
 * </p>
 *
 * @author Woody
 * @since 2017-05-06
 */
@TableName("shop_option")
public class ShopOption extends Model<ShopOption> {

    private static final long serialVersionUID = 1L;

    /**
     * 参数key
     */
	@TableId(value="option_key", type= IdType.AUTO)
	private Integer optionKey;
    /**
     * 参数数值
     */
	@TableField("option_value")
	private String optionValue;
    /**
     * 参数描述信息
     */
	@TableField("option_desc")
	private String optionDesc;


	public Integer getOptionKey() {
		return optionKey;
	}

	public void setOptionKey(Integer optionKey) {
		this.optionKey = optionKey;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public String getOptionDesc() {
		return optionDesc;
	}

	public void setOptionDesc(String optionDesc) {
		this.optionDesc = optionDesc;
	}

	@Override
	protected Serializable pkVal() {
		return this.optionKey;
	}

}
