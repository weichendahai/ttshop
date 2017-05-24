package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopFeedback;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.PoShopFeedback;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangzhen
 * @since 2017-05-08
 */
public interface IShopFeedbackService extends IService<ShopFeedback> {
     //获取用户反馈列表
    String getFeedBack(String user_id, String page_no);
    //提交反馈
    String submitFeedBack(String user_id, String fid, String img_addr, String content);
    //反馈阅读状态
    String readFeedBack(String user_id, String id);

    /**
     * 显示用户反馈的主题列表或者主题下的信息列表
     * @param map
     * @return
     */
    List<PoShopFeedback> selectShopFeedbackThemeList(Map<String,Object> map);

    /**
     * 查询用户的反馈主题列表数量
     * @param map
     * @return
     */
    Integer selectShopFeedbackThemeCount(Map<String,Object> map);

    /**
     * 根据ID查询最新未读消息
     * @param feedbackId
     * @return
     */
    /*PoShopFeedback selectRecentNotReadFeedbackById(Integer feedbackId );*/

    /**
     * 根据主题的ID显示该主题下的信息列表
     * @param feedbackId
     * @return
     */
    List<PoShopFeedback> selectShopFeedbackMessageList(Integer feedbackId);

    /**
     * 通过fid更改未读状态
     * @param feedbackId
     * @return
     */
    boolean updateShopFeedbackByFid(Integer feedbackId);

    /**
     * 添加客服的回复消息
     * @param shopFeedback
     * @return
     */
    boolean insertShopFeedback(ShopFeedback shopFeedback);

}
