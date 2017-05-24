package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopItemProperties;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性表，型号，颜色，容量等 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopItemPropertiesService extends IService<ShopItemProperties> {
	//添加商品描述
    void addShopItemPropertiesToRedis(ShopItemProperties shopItemProperties);
    //删除对应的属性
    void delShopItemProperties(ShopItemProperties shopItemProperties );
    //跟新对应的属性
    void updateitem_properties(ShopItemProperties shopItemProperties);

    /**
     * 获取商品属性列表
     * @param map
     * @return
     */
    List<ShopItemProperties> selectMultiShopItemPropertyList(Map<String,Object> map);

    /**
     * 获取商品列表总数
     * @param map
     * @return
     */
    Integer selectMultiShopItemPropertyCount(Map<String,Object> map);

}
