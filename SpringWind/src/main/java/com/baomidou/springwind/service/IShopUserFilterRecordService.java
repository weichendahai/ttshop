package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopUserFilterRecord;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Woody
 * @since 2017-05-01
 */
public interface IShopUserFilterRecordService extends IService<ShopUserFilterRecord> {

    /**
     * @param userIds 用户的ID
     * @param reason 0试用商品筛选，1优惠券筛选，2红包筛选
     * @param schemeId shop_user_filter_scheme表的id
     * @return
     */
    boolean  addShopUserFilterRecordByUserIds(List<Integer> userIds,Integer reason,Integer schemeId);
}
