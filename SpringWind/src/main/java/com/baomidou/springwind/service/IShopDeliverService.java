package com.baomidou.springwind.service;

/**
 *
 * Permission 表数据服务层接口
 *
 */
public interface IShopDeliverService {
	//未发货订单
	String getDeliveryOrders(String auth_id);
	//发货
	String orderdeliver(String order_id, String express_code);

	String getUserreFunds(String auth_id);
    //退货
	String refundFeedBack(String refund_id, String type, String message);
}