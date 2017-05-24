package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;


/**
 * <p>
 * 商城商品表
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public class ShopItemInfo extends Model<ShopItemInfo> {

    private static final long serialVersionUID = 1L;
	private Integer id;
	private String evaluateUserId ;
	private String sharedUserId;
	private String itemName ;
	private String itemBigImageAddr;
	private String orderCount;
	private String price;
	private String propertyKey;
	private String propertyValue;
	private String grade ;
	private String evaluateCount ;
	private String sharedCount;
	private String itemDesc;
	private String distributionType;
	private String distributionSub;
	private String distributionCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEvaluateUserId() {
		return evaluateUserId;
	}

	public void setEvaluateUserId(String evaluateUserId) {
		this.evaluateUserId = evaluateUserId;
	}

	public String getSharedUserId() {
		return sharedUserId;
	}

	public void setSharedUserId(String sharedUserId) {
		this.sharedUserId = sharedUserId;
	}
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemBigImageAddr() {
		return itemBigImageAddr;
	}

	public void setItemBigImageAddr(String itemBigImageAddr) {
		this.itemBigImageAddr = itemBigImageAddr;
	}

	public String getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getEvaluateCount() {
		return evaluateCount;
	}

	public void setEvaluateCount(String evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	public String getSharedCount() {
		return sharedCount;
	}

	public void setSharedCount(String sharedCount) {
		this.sharedCount = sharedCount;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(String distributionType) {
		this.distributionType = distributionType;
	}

	public String getDistributionSub() {
		return distributionSub;
	}

	public void setDistributionSub(String distributionSub) {
		this.distributionSub = distributionSub;
	}

	public String getDistributionCount() {
		return distributionCount;
	}

	public void setDistributionCount(String distributionCount) {
		this.distributionCount = distributionCount;
	}

	public Integer getSortFactor() {
		return sortFactor;
	}

	public void setSortFactor(Integer sortFactor) {
		this.sortFactor = sortFactor;
	}



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
     * 排序种子，数值约小越靠前
     */
	private Integer sortFactor;
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
