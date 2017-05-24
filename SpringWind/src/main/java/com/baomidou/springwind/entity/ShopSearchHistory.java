package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 历史搜索记录表
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_search_history")
public class ShopSearchHistory extends Model<ShopSearchHistory> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户表
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 搜索历史关键词
     */
	@TableField("search_keyword")
	private String searchKeyword;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
