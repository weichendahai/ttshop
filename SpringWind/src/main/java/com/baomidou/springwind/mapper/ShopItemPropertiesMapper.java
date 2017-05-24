package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.ShopItemProperties;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 商品属性表，型号，颜色，容量等 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopItemPropertiesMapper extends BaseMapper<ShopItemProperties> {
    ShopItemProperties selectByKey(@Param("item_id")String item_id ,@Param("property_key")String property_key,@Param("property_value") String property_value);

    List<String> selecetListByItemId(@Param("item_id") String item_id);

    List<ShopItemProperties> selecetValueByItemId(@Param("item_id")String item_id,@Param("key")String key );

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