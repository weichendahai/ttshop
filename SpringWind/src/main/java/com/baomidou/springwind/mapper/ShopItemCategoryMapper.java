package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopItemCategory;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 商品分类表，配合商品使用 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopItemCategoryMapper extends BaseMapper<ShopItemCategory> {

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
}