package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.ShopUserRefund;
import com.baomidou.springwind.entity.po.PoShopUserRefund;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 用户退款记录表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
public interface ShopUserRefundMapper extends BaseMapper<ShopUserRefund> {
    List<PoShopUserRefund> selectPoUserRefundList();
   //获取退款记录列表
    List<ShopUserRefund> getreFundList(@Param("user_id")String user_id,@Param("page_no")int page_no);
  //退货列表
    List<ShopUserRefund> selectBytype();

    /**
     * 查询所有退款列表
     * @param map
     * @return
     */
    List<PoShopUserRefund> selectMultiShopUserRefundList(Map<String,Object> map);

    /**
     * 查询所有退款列表的数量
     * @param map
     * @return
     */
    Integer selectMultiShopUserRefundCount(Map<String,Object> map);
}