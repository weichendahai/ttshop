package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopCoupon;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 优惠券表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopCouponService extends IService<ShopCoupon> {

    String getCoupon(String user_id, String page_no,String state);
}
