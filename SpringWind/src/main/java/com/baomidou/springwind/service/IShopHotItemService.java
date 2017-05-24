package com.baomidou.springwind.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.ShopHotItem;

import java.util.List;

/**
 * <p>
 * 爆款商品，大多数都是商品的冗余字段，应该存放在Redis中 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
public interface IShopHotItemService extends IService<ShopHotItem> {

    /**
     * 获取上一期爆款商品列表
     * @param _index
     * @param _size
     * @return
     */
    List<ShopHotItem> selectShopHotItemLimit(Integer _index,Integer _size);

    /**
     * 获取最新的爆款商品列表
     *
     * @return
     */
    List<ShopHotItem> selectUpdateShopHotItemList();

    /**
     * 查询最近一期期数
     */
    Integer getMaxSessionNo();

    boolean delShopHotItemByIdAndSessionNo(Integer id,Integer sessionNo);

    List<ShopHotItem> selectHotItemSessionNoAndDate();
    //获取爆款列表
    String getHotitemList(Long page_no, String season_no);

    //后台获取爆款列表信息
    void getInfoFromServer(ShopHotItem shopHotItem );
    //后台删除爆款列表
    void delInfoFromServer(ShopHotItem shopHotItem);
    //修改爆款列表
    void dInfoFromServer(ShopHotItem shopHotItem);

    /**
     * 添加爆款列表到redis
     * @param list
     */
    void addHotItemListToRedis(List<ShopHotItem> list);
}
