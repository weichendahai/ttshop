package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.springwind.entity.ShopItem;

import java.io.Serializable;

/**
 * <p>
 * 设置首页banner表
 * </p>
 *
 * @author Woody
 * @since 2017-04-06
 */
@TableName("shop_banner")
public class PoShopBanner extends Model<PoShopBanner> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商品id
     */
	@TableField("item_id")
	private Integer itemId;
    /**
     * 排序因子
     */
	@TableField("sort_factor")
	private Integer sortFactor;
    /**
     * 海报地址
     */
	@TableField("poster_image_addr")
	private String posterImageAddr;
	/**
	 * 排序因子
	 */
	@TableField("state")
	private Integer state;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	private ShopItem shopItem;

	public ShopItem getShopItem() {
		return shopItem;
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

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

	public Integer getSortFactor() {
		return sortFactor;
	}

	public void setSortFactor(Integer sortFactor) {
		this.sortFactor = sortFactor;
	}

	public String getPosterImageAddr() {
		return posterImageAddr;
	}

	public void setPosterImageAddr(String posterImageAddr) {
		this.posterImageAddr = posterImageAddr;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
