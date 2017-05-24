package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.ShopBonusPayment;
import com.baomidou.springwind.entity.po.PoShopBonusPayment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 佣金提现表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
public interface ShopBonusPaymentMapper extends BaseMapper<ShopBonusPayment> {

     List<ShopBonusPayment> selectByUserId(@Param("user_id") String user_id,@Param("page_no") int page_no );
      void insertBonusPayment(@Param("bp")ShopBonusPayment bp);

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
}