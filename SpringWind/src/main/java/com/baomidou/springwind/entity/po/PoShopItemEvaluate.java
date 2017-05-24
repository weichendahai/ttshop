package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 用户点评表，记录点评信息
 * </p>
 *
 * @author Woody
 * @since 2017-04-10
 */
@TableName("shop_item_evaluate")
public class PoShopItemEvaluate extends Model<PoShopItemEvaluate> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 点评商品id
     */
	@TableField("item_id")
	private Integer itemId;
    /**
     * 优点
     */
	private String advantage;
    /**
     * 缺点
     */
	private String disadvantage;
    /**
     * 点评的图片地址
     */
	@TableField("evaluate_image_addr")
	private String evaluateImageAddr;
    /**
     * 点评的视频地址
     */
	@TableField("evaluate_vedio_addr")
	private String evaluateVedioAddr;
    /**
     * 第一个相关产品id
     */
	@TableField("other_item_ids")
	private String otherItemIds;

    /**
     * 评分，按照100分屏，最后的结果除以10，避免小数
     */
	private Integer grade;
    /**
     * 点评创建时间
     */
	@TableField("create_date")
	private Date createDate;

	/**
	 * 0表示正常，1表示删除
	 */
	@TableField("evaluate_state")
	private Integer evaluateState;

	public Integer getEvaluateState() {
		return evaluateState;
	}

	public void setEvaluateState(Integer evaluateState) {
		this.evaluateState = evaluateState;
	}

	private ShopItem shopItem;

	private ShopUser shopUser;

	public ShopItem getShopItem() {
		return shopItem;
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	public ShopUser getShopUser() {
		return shopUser;
	}

	public void setShopUser(ShopUser shopUser) {
		this.shopUser = shopUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	public String getDisadvantage() {
		return disadvantage;
	}

	public void setDisadvantage(String disadvantage) {
		this.disadvantage = disadvantage;
	}

	public String getEvaluateImageAddr() {
		return evaluateImageAddr;
	}

	public void setEvaluateImageAddr(String evaluateImageAddr) {
		this.evaluateImageAddr = evaluateImageAddr;
	}

	public String getEvaluateVedioAddr() {
		return evaluateVedioAddr;
	}

	public void setEvaluateVedioAddr(String evaluateVedioAddr) {
		this.evaluateVedioAddr = evaluateVedioAddr;
	}

	public String getOtherItemIds() {
		return otherItemIds;
	}

	public void setOtherItemIds(String otherItemIds) {
		this.otherItemIds = otherItemIds;
	}

	public String getGrade() {
		if(grade!=0){
			String s = Integer.toString(grade);
			s=s.substring(0,s.length()-1);
			int g = grade%10;
			return s+"."+g;
		}
		return 0+"";
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getCreateDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createDate);
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
