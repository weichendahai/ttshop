package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopCouponCondition;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.PoShopCouponCondition;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券使用条件表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopCouponConditionService extends IService<ShopCouponCondition> {

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

    /**
     * 生成优惠券
     * @param poShopCouponCondition
     * @return
     */
    boolean addPoShopCouponCondition(PoShopCouponCondition poShopCouponCondition);
}
