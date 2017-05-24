package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 爆款商品，大多数都是商品的冗余字段，应该存放在Redis中
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@TableName("shop_hot_item")
public class ShopHotItem extends Model<ShopHotItem> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商品id
     */
	@TableField("item_id")
	private Integer itemId;
    /**
     * 商品名称
     */
	@TableField("item_name")
	private String itemName;
    /**
     * 商品图标地址
     */
	@TableField("item_icon_addr")
	private String itemIconAddr;
    /**
     * 点评数量
     */
	@TableField("evaluate_count")
	private Integer evaluateCount;
    /**
     * 评分
     */
	private Integer grade;
    /**
     * 价格
     */
	private Integer price;
    /**
     * 点评优点
     */
	private String advantage;
    /**
     * 排序因子
     */
	@TableField("sort_factor")
	private Integer sortFactor;
    /**
     * 阶段，期数，爆款商品列表，每隔一段时间会更新一次，此数据就是维护爆款列表时，自动增加的
     */
	@TableField("session_no")
	private Integer sessionNo;
    /**
     * 销售的数量,根据shop_order_item表获取,属于冗余数据
     */
	private Integer amount;
    /**
     * 生成列表的时间
     */
	@TableField("create_date")
	private Date createDate;


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

	public Integer getEvaluateCount() {
		return evaluateCount;
	}

	public void setEvaluateCount(Integer evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	public Integer getSortFactor() {
		return sortFactor;
	}

	public void setSortFactor(Integer sortFactor) {
		this.sortFactor = sortFactor;
	}

	public Integer getSessionNo() {
		return sessionNo;
	}

	public void setSessionNo(Integer sessionNo) {
		this.sessionNo = sessionNo;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
