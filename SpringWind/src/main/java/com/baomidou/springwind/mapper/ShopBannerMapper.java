package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopBanner;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.po.PoBanner;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 设置首页banner表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopBannerMapper extends BaseMapper<ShopBanner> {

     List<PoBanner> selectShopBannerList();

     /**
      * 获取轮播商品列表
      * @return
      */
     List<ShopBanner> selectMultiShopBanner(Map<String,Object> map);

     /**
      * 获取轮播商品总数
      * @return
      */
     Integer selectMultiShopBannerCount();

     /**
      * 查询轮播的商品名称
      * @param map
      * @return
      */
     List<ShopBanner> selectShopItemNameBannerList(Map<String,Object> map);
}