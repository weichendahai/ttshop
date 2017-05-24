package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopUserPromo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户拥有的优惠券红包表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopUserPromoService extends IService<ShopUserPromo> {

    List<ShopUserPromo> selectMulitShopUserPromoList(Integer _index,Integer _size);

    Integer selectMulitShopUserPromoListTotal();

    /**
     * 批量生成优惠券
     * @param userIds
     * @param userPromo
     * @return
     */
    boolean createCouponBatch(List<Integer> userIds,ShopUserPromo userPromo);
}
