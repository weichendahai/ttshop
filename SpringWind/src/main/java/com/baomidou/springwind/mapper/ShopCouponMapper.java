package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopCoupon;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 优惠券表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopCouponMapper extends BaseMapper<ShopCoupon> {
    String getCoupon(@Param("user_id")String user_id, @Param("page_no")String page_no);

}