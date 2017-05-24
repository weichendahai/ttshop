package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopSample;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试用表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopSampleService extends IService<ShopSample> {

    String getSampleList(String user_id, String page_no, String list_type);

    String applySample(String user_id, String sample_id);

    /**
     * 获取试用列表
     * @param map
     * @return
     */
    List<ShopSample> selectMultiShopSampleList(Map<String,Object> map);

    /**
     * 获取列表的数量
     * @param map
     * @return
     */
    Integer selectMultiShopSampleCount(Map<String,Object> map);

    /**
     * 添加试用商品
     * @param shopSample
     * @return
     */
    Integer insertShopSample(ShopSample shopSample);

    /**
     * 查询未试用的商品名称
     * @param map
     * @return
     */
    List<ShopSample> selectItemNameList(Map<String,Object> map);

    /**
     * 根据商品名称和一级分类名称和二级分类名称查询未试用商品的名称
     * @param map
     * @return
     */
    List<ShopSample> selectMultiShopSampleItemNameList(Map<String,Object> map);

    /**
     * 根据商品名称和一级分类名称和二级分类名称查询未试用商品的名称的数量
     * @param map
     * @return
     */
    Integer selectMultiShopSampleItemNameCount(Map<String,Object> map);



}
