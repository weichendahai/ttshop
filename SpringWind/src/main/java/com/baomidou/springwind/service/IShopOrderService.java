package com.baomidou.springwind.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.ShopExportOrder;
import com.baomidou.springwind.entity.ShopOrder;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopOrderService extends IService<ShopOrder> {

   List<ShopOrder> selectMultiShopOrderList(Integer _index,Integer _size,String sortField);

    /**
     * 获取订单列表总数
     * @param map
     * @return
     */
    Integer selectMultiShopOrderListTotal(Map<String,Object> map);

    /**
     * 获取订单列表
     * @param map
     * @return
     */
    List<ShopOrder> selectMultiShopOrderListByMap(Map<String,Object> map);
   //获取用户所有订单
    String getOrderList(String user_id, String page_no, String order_state);

 /**
  * 修改订单状态或者添加快递编号
  * @param shopOrder
  * @return
  */
   Integer updateOrderState(ShopOrder shopOrder);
    /*取消订单*/
    String cancelOrder(String order_id, String user_id);
 /**
  * 修改订单状态或者添加快递编号
  * @param map
  * @return  List<ShopExportOrder>
  */
    List<ShopExportOrder> exportShopOrderListByMap(Map<String, Object> map);
}
