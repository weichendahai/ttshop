package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ShopSharedBonus;
import com.baomidou.springwind.entity.po.PoShopSharedBonus;
import com.baomidou.springwind.mapper.ShopSharedBonusMapper;
import com.baomidou.springwind.service.IShopSharedBonusService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户分享奖励表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopSharedBonusServiceImpl extends BaseServiceImpl<ShopSharedBonusMapper, ShopSharedBonus> implements IShopSharedBonusService {

    @Override
    public List<PoShopSharedBonus> selectMultiShopSharedBonusList(Map<String, Object> map) {
        return baseMapper.selectMultiShopSharedBonusList(map);
    }
}
