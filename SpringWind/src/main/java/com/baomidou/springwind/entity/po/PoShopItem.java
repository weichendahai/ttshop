package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.springwind.entity.ShopBrand;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopItemCategory;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商城商品表
 * </p>
 *
 * @author Woody
 * @since 2017-04-11
 */
@TableName("shop_item")
public class PoShopItem extends Model<PoShopItem> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商品名称
     */
	@TableField("item_name")
	private String itemName;
    /**
     * 商品图标
     */
	@TableField("item_icon_addr")
	private String itemIconAddr;
    /**
     * 商品详情使用图片
     */
	@TableField("info_image_addrs")
	private String infoImageAddrs;
    /**
     * 商品详细页面使用的上方大图
     */
	@TableField("info_big_image_addr")
	private String infoBigImageAddr;
    /**
     * 商品效果
     */
	@TableField("item_effect")
	private String itemEffect;
    /**
     * 商品成分
     */
	@TableField("item_compose")
	private String itemCompose;
    /**
     * 厂商id
     */
	@TableField("manufactor_id")
	private Integer manufactorId;
    /**
     * 售价
     */
	private Integer price;
    /**
     * 当前状态，创建，上架，下架
     */
	@TableField("item_state")
	private Integer itemState;
    /**
     * 一级分类
     */
	@TableField("item_category_primary")
	private Integer itemCategoryPrimary;
    /**
     * 二级分类
     */
	@TableField("item_category_sub")
	private Integer itemCategorySub;
    /**
     * 品牌id
     */
	@TableField("brand_id")
	private Integer brandId;
	@TableField("create_date")
	private Date createDate;
    /**
     * 排序种子，数值约小越靠前
     */
	@TableField("sort_factor")
	private Integer sortFactor;
    /**
     * 点评佣金比例，如果字段evaluate_bonus_value不为空，则此字段无效
     */
	@TableField("evaluate_bonus_rate")
	private Integer evaluateBonusRate;
    /**
     * 点评佣金金额，如果此字段不为空，则点评佣金比例无效
     */
	@TableField("evaluate_bonus_value")
	private Integer evaluateBonusValue;
    /**
     * 分享佣金比例，如果shared_bonus_value字段有值，则此字段无效
     */
	@TableField("shared_bonus_rate")
	private Integer sharedBonusRate;
    /**
     * 分享佣金金额，如果此字段有值，则分享佣金比例字段无效
     */
	@TableField("shared_bonus_value")
	private Integer sharedBonusValue;
    /**
     * 分享购买的折扣率,例如90就是打九折,如果discount_value有值，则此字段无效
     */
	@TableField("discount_rate")
	private Integer discountRate;
    /**
     * 分享购买的折扣价格,例如4500,如果此字段有值，则discount_rate无效
     */
	@TableField("discount_value")
	private Integer discountValue;

	private PoShopItemCategory poShopItemCategory;

	public PoShopItemCategory getPoShopItemCategory() {
		return poShopItemCategory;
	}

	public void setPoShopItemCategory(PoShopItemCategory poShopItemCategory) {
		this.poShopItemCategory = poShopItemCategory;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemIconAddr() {
		return itemIconAddr;
	}

	public void setItemIconAddr(String itemIconAddr) {
		this.itemIconAddr = itemIconAddr;
	}

	public String getInfoImageAddrs() {
		return infoImageAddrs;
	}

	public void setInfoImageAddrs(String infoImageAddrs) {
		this.infoImageAddrs = infoImageAddrs;
	}

	public String getInfoBigImageAddr() {
		return infoBigImageAddr;
	}

	public void setInfoBigImageAddr(String infoBigImageAddr) {
		this.infoBigImageAddr = infoBigImageAddr;
	}

	public String getItemEffect() {
		return itemEffect;
	}

	public void setItemEffect(String itemEffect) {
		this.itemEffect = itemEffect;
	}

	public String getItemCompose() {
		return itemCompose;
	}

	public void setItemCompose(String itemCompose) {
		this.itemCompose = itemCompose;
	}

	public Integer getManufactorId() {
		return manufactorId;
	}

	public void setManufactorId(Integer manufactorId) {
		this.manufactorId = manufactorId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getItemState() {
		return itemState;
	}

	public void setItemState(Integer itemState) {
		this.itemState = itemState;
	}

	public Integer getItemCategoryPrimary() {
		return itemCategoryPrimary;
	}

	public void setItemCategoryPrimary(Integer itemCategoryPrimary) {
		this.itemCategoryPrimary = itemCategoryPrimary;
	}

	public Integer getItemCategorySub() {
		return itemCategorySub;
	}

	public void setItemCategorySub(Integer itemCategorySub) {
		this.itemCategorySub = itemCategorySub;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getSortFactor() {
		return sortFactor;
	}

	public void setSortFactor(Integer sortFactor) {
		this.sortFactor = sortFactor;
	}

	public Integer getEvaluateBonusRate() {
		return evaluateBonusRate;
	}

	public void setEvaluateBonusRate(Integer evaluateBonusRate) {
		this.evaluateBonusRate = evaluateBonusRate;
	}

	public Integer getEvaluateBonusValue() {
		return evaluateBonusValue;
	}

	public void setEvaluateBonusValue(Integer evaluateBonusValue) {
		this.evaluateBonusValue = evaluateBonusValue;
	}

	public Integer getSharedBonusRate() {
		return sharedBonusRate;
	}

	public void setSharedBonusRate(Integer sharedBonusRate) {
		this.sharedBonusRate = sharedBonusRate;
	}

	public Integer getSharedBonusValue() {
		return sharedBonusValue;
	}

	public void setSharedBonusValue(Integer sharedBonusValue) {
		this.sharedBonusValue = sharedBonusValue;
	}

	public Integer getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}

	public Integer getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(Integer discountValue) {
		this.discountValue = discountValue;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
