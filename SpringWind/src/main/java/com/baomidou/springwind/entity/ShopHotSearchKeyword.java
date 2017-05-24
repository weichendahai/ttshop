package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 热搜榜
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_hot_search_keyword")
public class ShopHotSearchKeyword extends Model<ShopHotSearchKeyword> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 热词
     */
	@TableField("search_keyword")
	private String searchKeyword;
    /**
     * 热词排序因子，数值越小越靠前
     */
	@TableField("sort_factor")
	private String sortFactor;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getSortFactor() {
		return sortFactor;
	}

	public void setSortFactor(String sortFactor) {
		this.sortFactor = sortFactor;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
