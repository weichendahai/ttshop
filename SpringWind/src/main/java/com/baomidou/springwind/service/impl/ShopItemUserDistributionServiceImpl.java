package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ShopItemUserDistribution;
import com.baomidou.springwind.mapper.ShopItemUserDistributionMapper;
import com.baomidou.springwind.service.IShopItemUserDistributionService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品用户分布表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
@Service
public class ShopItemUserDistributionServiceImpl extends BaseServiceImpl<ShopItemUserDistributionMapper, ShopItemUserDistribution> implements IShopItemUserDistributionService {
    @Autowired
    private ShopItemUserDistributionMapper shopItemUserDistributionMapper;
/*
* 没添加一款商品向shop——user—item表添加一款记录*/
    @Override
    public void setItemUserDistribution(Integer item_id) {
        ShopItemUserDistribution SIUD=new ShopItemUserDistribution();
        SIUD.setItemId(item_id);
        SIUD.setType(1);
        SIUD.setSub(0);
        java.util.Random random=new java.util.Random();
        SIUD.setDistributionTypeDes("年龄");
        SIUD.setDistributionSubDesc("18岁一下");
        shopItemUserDistributionMapper.insert(SIUD);
        SIUD.setSub(1);
        SIUD.setCount(random.nextInt(10));
        SIUD.setDistributionSubDesc("18-24岁");
        shopItemUserDistributionMapper.insert(SIUD);
        SIUD.setSub(2);
        SIUD.setCount(random.nextInt(10));
        SIUD.setDistributionSubDesc("25-30岁");
        shopItemUserDistributionMapper.insert(SIUD);
        SIUD.setSub(3);
        SIUD.setCount(random.nextInt(10));
        SIUD.setDistributionSubDesc("30-39岁");
        shopItemUserDistributionMapper.insert(SIUD);
        SIUD.setSub(4);
        SIUD.setCount(random.nextInt(10));
        SIUD.setDistributionSubDesc("40岁以上");
        shopItemUserDistributionMapper.insert(SIUD);
        SIUD.setType(0);
        SIUD.setCount(random.nextInt(10));
        SIUD.setDistributionTypeDes("肤质");
        SIUD.setSub(0);
        SIUD.setCount(random.nextInt(10));
        SIUD.setDistributionSubDesc("干性");
        shopItemUserDistributionMapper.insert(SIUD);
        SIUD.setSub(1);
        SIUD.setCount(random.nextInt(10));
        SIUD.setDistributionSubDesc("油性");
        shopItemUserDistributionMapper.insert(SIUD);
        SIUD.setSub(2);
        SIUD.setCount(random.nextInt(10));
        SIUD.setDistributionSubDesc("中性");
        shopItemUserDistributionMapper.insert(SIUD);
        SIUD.setSub(3);
        SIUD.setCount(random.nextInt(10));
        SIUD.setDistributionSubDesc("混合");
        shopItemUserDistributionMapper.insert(SIUD);
        SIUD.setSub(4);
        SIUD.setCount(random.nextInt(10));
        SIUD.setDistributionSubDesc("敏感");
        shopItemUserDistributionMapper.insert(SIUD);
    }
}
