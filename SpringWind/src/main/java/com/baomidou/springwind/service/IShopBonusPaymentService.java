package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopBonusPayment;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.PoShopBonusPayment;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 佣金提现表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
public interface IShopBonusPaymentService extends IService<ShopBonusPayment> {
    String getBonus(String user_id, String page_no );

    String requestBonusPayment(String user_id);

    /**
     查询佣金申请列表
     * @param map
     * @return
     */
    List<PoShopBonusPayment> selectMultiBonusPaymentList(Map<String,Object> map);

    /**
     * 查询佣金申请的数量
     * @param ma
     * @return
     */
    Integer selectMultiBonusPaymentCount(Map<String,Object> ma);

    /**
     * 处理申请提现
     * @param bonusId
     * @return
     */
    boolean updateShopBonusPaymentState(Integer bonusId);
}
