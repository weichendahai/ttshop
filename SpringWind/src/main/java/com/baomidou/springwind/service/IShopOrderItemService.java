package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopOrderItem;
import com.baomidou.springwind.entity.po.PoShopOrderItem;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单商品关系表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopOrderItemService extends IService<ShopOrderItem> {

    //根据订单好查询订单项
    List<ShopOrderItem> selectOrderItemListByOrderId(Long oderId);

    /**
     * 查询订单详情
     * @param map
     * @return
     */
    List<PoShopOrderItem> selectMultiOrderItemList(Map<String,Object> map);

    /**
     * 查询订单详情里的商品属性
     * @param map
     * @return
     */
    /*List<PoShopOrderItem> selectMultiOrderItemPropertiesList(Map<String,Object> map);*/

}
