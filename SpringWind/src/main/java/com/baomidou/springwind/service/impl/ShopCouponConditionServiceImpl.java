package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ShopCoupon;
import com.baomidou.springwind.entity.ShopCouponCondition;
import com.baomidou.springwind.entity.po.PoShopCouponCondition;
import com.baomidou.springwind.mapper.ShopCouponConditionMapper;
import com.baomidou.springwind.mapper.ShopCouponMapper;
import com.baomidou.springwind.service.IShopCouponConditionService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券使用条件表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopCouponConditionServiceImpl extends BaseServiceImpl<ShopCouponConditionMapper, ShopCouponCondition> implements IShopCouponConditionService {

    @Autowired
    private ShopCouponMapper shopCouponMapper;

    @Override
    public List<PoShopCouponCondition> selectMultiShopCouponList(Map<String, Object> map) {
        return baseMapper.selectMultiShopCouponList(map);
    }

    @Override
    public Integer selectMultiShopCouponCount(Map<String, Object> map) {
        return baseMapper.selectMultiShopCouponCount(map);
    }

    //生成优惠券
    @Override
    public boolean addPoShopCouponCondition(PoShopCouponCondition pocc) {
        ShopCouponCondition shopCouponCondition = new ShopCouponCondition();
        ShopCoupon shopCoupon = new ShopCoupon();
        pocc.getShopCoupon().setCreateDate(new Date());
        shopCouponMapper.insert(pocc.getShopCoupon());
        Integer couponId = pocc.getShopCoupon().getId();
        shopCouponCondition.setCouponId(couponId);
        shopCouponCondition.setConditionDesc(pocc.getConditionDesc());
        shopCouponCondition.setConditionType(pocc.getConditionType());
        shopCouponCondition.setParameterFirst(pocc.getParameterFirst());
        shopCouponCondition.setParameterSecond(pocc.getParameterSecond());
        Integer add = baseMapper.insert(shopCouponCondition);
        if (add>0){
            return true;
        }
        return false;
    }
}
