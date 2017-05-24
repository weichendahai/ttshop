package com.baomidou.springwind.entity;

/**
 * Created by wangzhen on 2017/5/15.
 */
public class ShopExportOrder {
    private static final long serialVersionUID = 1L;
    private Long id;
    /*订单编号
    * */
    private String orderCode;
    /**
     * 用户id
     */
    private Long userId;
    /**
     *     0  代付款    1  代发货   2  待收货   3带评价 4已完成
     */
    private String  orderState;
    /*订单状态
    * */
    private String expressCode;
    /**
     * 订单金额，单位分，注意100是一元钱，均是整数，显示时，除以100显示。
     */
    private String  orderTotal;
    /**
     * 订单的收货地址id，关联用户地址表的id
     */
    private Integer orderAddrId;
    /**
     * 运费，单位分，100是一元
     */
    private String  freight;
    /**
     * 使用的优惠券id，shop_user_promo表的id
     */
    private Integer userCouponId;
    /**
     * 使用的红包id，shop_user_promo表的id
     */
    private Integer userGiftCardId;
    private String createDate;
    /*交易修改时间*/
    private String modifyDate;
    /**
     * 交易单号，微信交易流水号
     */
    private String transNumber;
    /**
     * 商品属性
     */
    private String propertyDesc;
    /**
     * 商品数量
     */
    private String itemCount;
    /**
     * 商品数量
     */
    private String itemName;
    /*
    * 用户昵称
    * */

    public String getPropertyDesc() {
        return propertyDesc;
    }

    public void setPropertyDesc(String propertyDesc) {
        this.propertyDesc = propertyDesc;
    }

    private String nickName;
    /*
    * 发货地址
    **/
    private String contact;
    /*
    * 收货地址
    * */
    private String address;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*
        * 手机号码
        * */
   private String telephone;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public Integer getOrderAddrId() {
        return orderAddrId;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setOrderAddrId(Integer orderAddrId) {
        this.orderAddrId = orderAddrId;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public Integer getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Integer userCouponId) {
        this.userCouponId = userCouponId;
    }

    public Integer getUserGiftCardId() {
        return userGiftCardId;
    }

    public void setUserGiftCardId(Integer userGiftCardId) {
        this.userGiftCardId = userGiftCardId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getTransNumber() {
        return transNumber;
    }

    public void setTransNumber(String transNumber) {
        this.transNumber = transNumber;
    }
    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
