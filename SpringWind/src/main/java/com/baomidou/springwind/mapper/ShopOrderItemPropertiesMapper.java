package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopOrderItemProperties;
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
 * @since 2017-03-31
 */
public interface ShopOrderItemPropertiesMapper extends BaseMapper<ShopOrderItemProperties> {

    /**
     * 根据订单号查看订单详情
     * @param orderCode
     * @return
     */
    List<ShopOrderItemProperties> selectShopOrderItemByOrderCode(Long orderCode);
    //获取对应的商拼信息
    List<ShopOrderItemProperties> seletctByItemId(@Param("id") Long id,@Param("itemId") Integer itemId,@Param("OrderitemId")Long OrderitemId);
   //获取每条订单的详情
   List< ShopOrderItemProperties> getItemOrder(@Param("orderId")Integer orderId,@Param("itemId") Integer itemId);

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