package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.ShopExportOrder;
import com.baomidou.springwind.entity.ShopOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
  * 订单表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopOrderMapper extends BaseMapper<ShopOrder> {

    Date getRecentDate();

   List<ShopOrder> selectMultiShopOrderList(@Param("_index") Integer _index,@Param("_size") Integer _size,@Param("sortField") String sortField);

    Integer selectMultiShopOrderListTotal(Map<String,Object> map);
    List<ShopOrder> selectMultiShopOrderListByMap(Map<String,Object> map);
    //搜索订单
    List<ShopOrder> selectMultiShopOrderListByMap1(Map<String,Object> map);
    //根据用户id获取所有的订单信息
    List<ShopOrder> selectOrderByUserId(@Param("user_id")String user_id, @Param("order_state")Integer order_state,@Param("page_no" )int page_no);
    //获取未发货的订单信息
    List<ShopOrder> selectUnDeliver();
    /*获取导出的订单列表信息*/
    List<ShopExportOrder> exportShopOrderListByMap();
}