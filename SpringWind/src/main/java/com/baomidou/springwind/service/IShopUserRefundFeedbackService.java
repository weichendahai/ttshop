package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopUserRefundFeedback;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.PoShopUserRefundFeedback;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
public interface IShopUserRefundFeedbackService extends IService<ShopUserRefundFeedback> {

    /**
     * 查询退款反馈信息
     * @param map
     * @return
     */
    List<PoShopUserRefundFeedback> selectUserRefundFeedbackList(Map<String,Object> map);
}
