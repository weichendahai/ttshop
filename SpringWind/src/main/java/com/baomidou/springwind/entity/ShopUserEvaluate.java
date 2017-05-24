package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 商城用户表
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
public class ShopUserEvaluate extends Model<ShopUserEvaluate> {

    private static final long serialVersionUID = 1L;
	private  int grade;
	private Long id;

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	private String userId;
	private String evaluateId;
	private String userHeadImageAdd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(String evaluateId) {
		this.evaluateId = evaluateId;
	}

	public String getUserHeadImageAdd() {
		return userHeadImageAdd;
	}

	public void setUserHeadImageAdd(String userHeadImageAdd) {
		this.userHeadImageAdd = userHeadImageAdd;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserEvaluateCoun() {
		return userEvaluateCoun;
	}

	public void setUserEvaluateCoun(String userEvaluateCoun) {
		this.userEvaluateCoun = userEvaluateCoun;
	}

	public String getUserSharedCount() {
		return userSharedCount;
	}

	public void setUserSharedCount(String userSharedCount) {
		this.userSharedCount = userSharedCount;
	}

	public String getUserDateOfBirth() {
		return userDateOfBirth;
	}

	public void setUserDateOfBirth(String userDateOfBirth) {
		this.userDateOfBirth = userDateOfBirth;
	}

	public String getItemAdvantage() {
		return itemAdvantage;
	}

	public void setItemAdvantage(String itemAdvantage) {
		this.itemAdvantage = itemAdvantage;
	}

	public String getItemDisadvatage() {
		return itemDisadvatage;
	}

	public void setItemDisadvatage(String itemDisadvatage) {
		this.itemDisadvatage = itemDisadvatage;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	private String userNickname;
	private String userEvaluateCoun;
	private String userSharedCount;
	private String userDateOfBirth;
	private String itemAdvantage;
	private String itemDisadvatage;
	private Date createDate;

	public String getEvaluateImageAddr() {
		return evaluateImageAddr;
	}

	public void setEvaluateImageAddr(String evaluateImageAddr) {
		this.evaluateImageAddr = evaluateImageAddr;
	}

	private String evaluateImageAddr;

	public String getEvaluateState() {
		return evaluateState;
	}

	public void setEvaluateState(String evaluateState) {
		this.evaluateState = evaluateState;
	}

	private String evaluateState;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
