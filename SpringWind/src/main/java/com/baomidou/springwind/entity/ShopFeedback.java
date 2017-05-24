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
 * 
 * </p>
 *
 * @author Woody
 * @since 2017-05-05
 */
@TableName("shop_feedback")
public class ShopFeedback extends Model<ShopFeedback> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 反馈id，如果为0，则表示为此此反馈的第一条,每条反馈有相同的fid,按照时间顺序来区分回复时间
     */
	private Integer fid;
    /**
     * 如果是用户则保存用户user_id，如果是商城客服人员保存为-1
     */
	private Integer from;
    /**
     * 回复内容
     */
	private String content;
    /**
     * 图片地址
     */
	@TableField("img_addr")
	private String imgAddr;
    /**
     * 阅读的时间,如果为空,表示还没阅读,是未读状态
     */
	@TableField("read_date")
	private Date readDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgAddr() {
		return imgAddr;
	}

	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
