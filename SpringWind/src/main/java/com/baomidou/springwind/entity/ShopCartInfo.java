package com.baomidou.springwind.entity;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 *
 * 购物车
 *
 */
public class ShopCartInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String itemId;
	private String endTime;
	private String evaluateUserId;
	private String sharedUserId;
	private String itemName;

	public String getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}

	private String itemIcon;
    private String cartItemId;
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public String getItemIcon() {
		return itemIcon;
	}

	public void setItemIcon(String itemIcon) {
		this.itemIcon = itemIcon;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	private JSONArray itemPropertys;

	public JSONArray getItemPropertys() {
		return itemPropertys;
	}

	public void setItemPropertys(JSONArray itemPropertys) {
		this.itemPropertys = itemPropertys;
	}

	private int itemCount;
	private int itemPrice;


}
