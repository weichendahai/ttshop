package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopUserRefundFeedback;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.po.PoShopUserRefundFeedback;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
public interface ShopUserRefundFeedbackMapper extends BaseMapper<ShopUserRefundFeedback> {


    /**
     * 查询退款反馈信息
     * @param map
     * @return
     */
    List<PoShopUserRefundFeedback> selectUserRefundFeedbackList(Map<String,Object> map);
}