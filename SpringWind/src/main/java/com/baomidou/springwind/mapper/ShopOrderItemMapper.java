package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopOrderItem;
import com.baomidou.springwind.entity.po.PoShopOrderItem;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 订单商品关系表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopOrderItemMapper extends BaseMapper<ShopOrderItem> {
    //更具订单号获取订单项
    List<ShopOrderItem> selectOrderItemListByOrderId(Long orderId);
   //查询该商品对应订单数量
    int selectByItemId(@Param("item_id") String item_id);

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
    List<PoShopOrderItem> selectMultiOrderItemPropertiesList(Map<String,Object> map);

    /**
     * 查询定详情里每个商品的数量
     * @param map
     * @return
     */
    Integer selectMultiOrderItemCount(Map<String,Object> map);
}