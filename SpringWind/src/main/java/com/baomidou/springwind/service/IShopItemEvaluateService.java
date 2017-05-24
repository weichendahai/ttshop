package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopItemEvaluate;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.PoShopItemEvaluate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户点评表，记录点评信息 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopItemEvaluateService extends IService<ShopItemEvaluate> {
    //获取用户评价列表
    String getUserevaluateList(@Param("user_id") String user_id,@Param("page_no")String page_no ,@Param("list_type")String list_type,@Param("only_bonus")String only_bonus);
    //写点评
    String addEvaluate(String user_id, String item_id, String item_advantage, String item_disadvatage, String evaluate_image_addr, String evaluate_vedio_addr, String grade, String other_item_ids);

    /**
     * 获取点评商品列表
     * @return
     */
    List<ShopItemEvaluate> selectMultiShopItemEvaluateList(Map<String,Object> map);

    /**
     * 获取点评商品总数
     * @return
     */
    Integer selectMultiShopItemEvaluateCount(Map<String,Object> map);
   //获取商品评级列表
    String getItemevaluateList(String item_id,String page_no);
   //获取点评详情
    String getEvaluateInfo(String user_id, String evaluate_id,String evaluate_shared_id);

    /**
     * 查询所有未删除的点评
     * @param map
     * @return
     */
    List<PoShopItemEvaluate> selectEvaluateListAll(Map<String,Object> map);

    /**
     * 查询所有未删除点评的数量
     * @param map
     * @return
     */
    Integer selectEvaluateListAllCount(Map<String,Object> map);
}
