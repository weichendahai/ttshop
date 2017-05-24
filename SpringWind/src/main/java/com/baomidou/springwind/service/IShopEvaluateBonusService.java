package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopEvaluateBonus;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.PoShopEvaluateBonus;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 点评佣金表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopEvaluateBonusService extends IService<ShopEvaluateBonus> {

    /**
     * 查询点评 佣金详细信息
     * @param map
     * @return
     */
    List<PoShopEvaluateBonus> selectMultiShopEvaluateSharedList(Map<String,Object> map);
    /**
     * 计算佣金的方法
     */
    void getShopEvaluateBonus(String order_id);
}
