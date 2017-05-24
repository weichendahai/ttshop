package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ShopUserPromo;
import com.baomidou.springwind.mapper.ShopUserPromoMapper;
import com.baomidou.springwind.service.IShopUserPromoService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户拥有的优惠券红包表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopUserPromoServiceImpl extends BaseServiceImpl<ShopUserPromoMapper, ShopUserPromo> implements IShopUserPromoService {

    @Override
    public Integer selectMulitShopUserPromoListTotal() {

        return baseMapper.selectMulitShopUserPromoListTotal();
    }

    @Override
    public List<ShopUserPromo> selectMulitShopUserPromoList(Integer _index,Integer _size) {
      return baseMapper.selectMulitShopUserPromoList(_index, _size);
    }

    /**
     * 批量生成优惠券
     * @param userIds
     * @param userPromo
     * @return
     */
    @Override
    public boolean createCouponBatch(List<Integer> userIds, ShopUserPromo userPromo) {
        List<ShopUserPromo> list = new ArrayList<ShopUserPromo>();
        ShopUserPromo sup = null;
        for(Integer userId:userIds){
            sup = new ShopUserPromo();
            sup.setUserId(userId);
            sup.setEndDate(userPromo.getEndDate());
            sup.setGrantDate(new Date());
            sup.setPromoId(userPromo.getPromoId());
            sup.setPromoType(0);
            sup.setState(0);
            list.add(sup);
        }
        boolean create = insertBatch(list);
        return create;
    }
}
