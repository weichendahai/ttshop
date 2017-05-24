package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopItemCategory;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类表，配合商品使用 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopItemCategoryService extends IService<ShopItemCategory> {

    //向redis提供二级分类
    void addCategory(ShopItemCategory  shopItemCategory);
    //向redis删除二级分类
    void delCategory(ShopItemCategory  shopItemCategory);
    //跟新数据
    void updateCategory(ShopItemCategory  shopItemCategory) ;

    /**
     * 获取分类列表
     * @param map
     * @return
     */
    List<ShopItemCategory> selectMultiShopItemCategoryList(Map<String,Object> map);

    /**
     * 获取分类列表的总数
     * @param map
     * @return
     */
    Integer selectMultiShopItemCategoryCount(Map<String,Object> map);
    //
    void rediserji();
}
