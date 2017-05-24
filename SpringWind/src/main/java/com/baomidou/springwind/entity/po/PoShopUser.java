package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.springwind.entity.ShopUserAddr;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 商城用户表
 * </p>
 *
 * @author Woody
 * @since 2017-04-11
 */
@TableName("shop_user")
public class PoShopUser extends Model<PoShopUser> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	private String nickname;
    /**
     * 头像地址
     */
	@TableField("head_image_addr")
	private String headImageAddr;
    /**
     * 性别，0，男，1，女
     */
	private Integer sex;
	@TableField("mobile_phone")
	private String mobilePhone;
	@TableField("date_of_birth")
	private Date dateOfBirth;
    /**
     * 肤质
     */
	@TableField("skin_type")
	private Integer skinType;
	private String email;
    /**
     * 默认地址
     */
	@TableField("default_addr")
	private Integer defaultAddr;
    /**
     * 分享数量
     */
	@TableField("shard_count")
	private Integer shardCount;
    /**
     * 点评数量
     */
	@TableField("evaluate_count")
	private Integer evaluateCount;
    /**
     * 分享佣金
     */
	@TableField("shard_bonus")
	private Integer shardBonus;
    /**
     * 点评佣金
     */
	@TableField("evaluate_bonus")
	private Integer evaluateBonus;
	@TableField("open_id")
	private String openId;
	@TableField("union_id")
	private String unionId;
    /**
     * 用户状态,0是正常,1为不能,2
     */
	private Integer state;

	private ShopUserAddr shopUserAddr;

	public ShopUserAddr getShopUserAddr() {
		return shopUserAddr;
	}

	public void setShopUserAddr(ShopUserAddr shopUserAddr) {
		this.shopUserAddr = shopUserAddr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImageAddr() {
		return headImageAddr;
	}

	public void setHeadImageAddr(String headImageAddr) {
		this.headImageAddr = headImageAddr;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getDateOfBirth() {
		return new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth);
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getSkinType() {
		return skinType;
	}

	public void setSkinType(Integer skinType) {
		this.skinType = skinType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getDefaultAddr() {
		return defaultAddr;
	}

	public void setDefaultAddr(Integer defaultAddr) {
		this.defaultAddr = defaultAddr;
	}

	public Integer getShardCount() {
		return shardCount;
	}

	public void setShardCount(Integer shardCount) {
		this.shardCount = shardCount;
	}

	public Integer getEvaluateCount() {
		return evaluateCount;
	}

	public void setEvaluateCount(Integer evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	public Integer getShardBonus() {
		return shardBonus;
	}

	public void setShardBonus(Integer shardBonus) {
		this.shardBonus = shardBonus;
	}

	public Integer getEvaluateBonus() {
		return evaluateBonus;
	}

	public void setEvaluateBonus(Integer evaluateBonus) {
		this.evaluateBonus = evaluateBonus;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
