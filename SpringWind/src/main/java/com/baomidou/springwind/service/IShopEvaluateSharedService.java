package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopEvaluateShared;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.PoShopEvaluateShared;
import com.baomidou.springwind.entity.po.PoShopSharedBonus;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 点评的分享记录表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopEvaluateSharedService extends IService<ShopEvaluateShared> {
   //点评和分享榜单
    String evaluateRanking(String ranking_type, String cycle, String season_no, String user_id,String page_no);

    /**
     * 获取分享列表
     * @param map
     * @return
     */
    List<PoShopEvaluateShared> selectMultiShopEvaluateShared(Map<String,Object> map);

    /**
     * 获取分享列表的条目数
     * @param map
     * @return
     */
    Integer selectMultiShopEvaluateSharedCount(Map<String,Object> map);
     //转发朋友圈
    String sharedEvaluate(String evaluate_id, String user_id);
}
