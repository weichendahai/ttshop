package com.baomidou.springwind.service;

/**
 *
 * Permission 表数据服务层接口
 *
 */
public interface IShopCartService {
	//添加购物车接口
	String addItemtoCart( String user_id,String item_id,String evaluate_shared_id ,String property_key ,String property_value);
	//获取购物车接口
	String getCartItems(String user_id);
	//修改购物车接口
	String modifyCartItem(String user_id,String item_cont ,String cartItemId);
	//停止时间
	String stopCartCd(String user_id,String cart_item_ids);
	//开始计时
	String resumeCartcd( String user_id);
	//提交订单
	String submitOrder(  String user_id,String order_addr_id, String user_coupon_id, String user_gift_card_id);

	String getSampleList(String user_id, String page_no, String list_type);
    //立即购买
	String buyNow(String item_id,String user_id,String evaluate_shared_id);
  //调用微信接口
	String isUserLogin(String code);
  //获取微信sdk
	String getWxSdk(String wxurl);
   //微信支付
	String payForWx(String order_id,String ip,String wxurl);
   //微信支付判断
	String paySuccess(String order_id, String success,String trans_number);
}