package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopCouponCondition;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.po.PoShopCouponCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 优惠券使用条件表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopCouponConditionMapper extends BaseMapper<ShopCouponCondition> {

    List<ShopCouponCondition> selectByUserId(@Param("coupon_id") String coupon_id);

    /**
     * 查询优惠券列表
     * @param map
     * @return
     */
    List<PoShopCouponCondition> selectMultiShopCouponList(Map<String,Object> map);

    /**
     * 查询优惠券列表数量
     * @param map
     * @return
     */
    Integer selectMultiShopCouponCount(Map<String,Object> map);
}