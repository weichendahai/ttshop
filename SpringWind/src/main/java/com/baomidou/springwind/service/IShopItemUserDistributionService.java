package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopItemUserDistribution;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 商品用户分布表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
public interface IShopItemUserDistributionService extends IService<ShopItemUserDistribution> {
    //后台添加商品信息的时候调用次接口
	void setItemUserDistribution(Integer item_id);
}
