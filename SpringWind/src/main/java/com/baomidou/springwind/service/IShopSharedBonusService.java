package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopSharedBonus;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.PoShopSharedBonus;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户分享奖励表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopSharedBonusService extends IService<ShopSharedBonus> {

    /**
     * 查询分享详情信息
     * @param map
     * @return
     */
    List<PoShopSharedBonus> selectMultiShopSharedBonusList(Map<String,Object> map);

}
