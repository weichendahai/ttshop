package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopOrderItemProperties;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单商品关系表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-31
 */
public interface IShopOrderItemPropertiesService extends IService<ShopOrderItemProperties> {

    /**
     * 根据订单号查看订单详情
     * @param orderCode
     * @return
     */
    List<ShopOrderItemProperties> selectShopOrderItemByOrderCode(Long orderCode);
    //获取订单列表
    String getOrderList(String user_id, String page_no, String order_state);
   //获取订单详情
    String getOrderInfo(String user_id, String order_id);

    /**
     * 查询订单详情列表
     * @param map
     * @return
     */
    List<ShopOrderItemProperties> selectMultiShopOrderItemPropertiesList(Map<String,Object> map);

    /**
     * 查询订单详情的数量
     * @param map
     * @return
     */
    Integer selectMultiShopOrderItemPropertiesCount(Map<String,Object> map);

    /**
     * 查询订单详情里的属性
     * @param map
     * @return
     */
    List<ShopOrderItemProperties> selectMultiShopOrderItemProperties(Map<String,Object> map);
}
