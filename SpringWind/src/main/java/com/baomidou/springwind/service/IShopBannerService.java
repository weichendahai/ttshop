package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopBanner;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.PoBanner;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设置首页banner表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopBannerService extends IService<ShopBanner> {

    List<PoBanner> selectShopBannerList();
   //获取banner列表
    String getBannerList();
    //添加banner列表
    void addbannerList(ShopBanner shopBanner);
    //删除banner
    void delbannerList(ShopBanner shopBanner);

    /**
     * 轮播商品列表
     * @return
     */
    List<ShopBanner> selectItemNameBannerList(Map<String,Object> map);

    /**
     * 获取轮播商品总数
     * @return
     */
    Integer selectMultiShopBannerCount();

    List<ShopBanner> selectMultiShopBanner(Map<String,Object> map);

   /*
   * 商品下架关联轮播状态*/
    void updateState(Integer id);
  /*商品上架后轮播有效*/
    void updateState1(Integer id);
}
