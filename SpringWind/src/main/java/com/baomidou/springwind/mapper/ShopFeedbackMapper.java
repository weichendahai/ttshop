package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopFeedback;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.po.PoShopFeedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Woody
 * @since 2017-05-05
 */
public interface ShopFeedbackMapper extends BaseMapper<ShopFeedback> {
    //获取搜索的用户首次反馈
    List<ShopFeedback> selectByUserId(@Param("user_id") String user_id, @Param("page_no")int page_no);

    /**
     * 查询用户的反馈主题列表
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
     * 根据ID查询用户最新未读消息
     * @param feedbackId
     * @return
     */
    PoShopFeedback selectRecentNotReadFeedbackById(@Param("feedbackId") Integer feedbackId );

    /**
     * 根据主题查询主题下的所有消息
     * @param feedbackId
     * @return
     */
    List<PoShopFeedback> selectShopFeedbackMessageList(@Param("feedbackId") Integer feedbackId );

}