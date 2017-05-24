package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopItemUserDistribution;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 商品用户分布表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
public interface ShopItemUserDistributionMapper extends BaseMapper<ShopItemUserDistribution> {
    //获取描述
    List<String> selectByItemId(@Param("item_id")String item_id);
     //获取类型描述
    String selectDesc(@Param("type")String type,@Param("item_id") String item_id);

    List<ShopItemUserDistribution> selectByType(@Param("item_id")String item_id, @Param("type")String type);
}