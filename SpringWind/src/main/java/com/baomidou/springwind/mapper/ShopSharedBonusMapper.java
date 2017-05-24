package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.ShopSharedBonus;
import com.baomidou.springwind.entity.po.PoShopEvaluateShared;
import com.baomidou.springwind.entity.po.PoShopSharedBonus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 用户分享奖励表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopSharedBonusMapper extends BaseMapper<ShopSharedBonus> {
  //获取分享榜单
    List<ShopSharedBonus> selectRank(@Param("startDate") String startDate,@Param("endDate")  String endDate,@Param("page_no") int page_no );
   //获取分享的查询条数
    Integer getCount(@Param("startDate")String startDate,@Param("endDate") String endDate);

    /**
     * 查询分享详情
     * @param map
     * @return
     */
    List<PoShopSharedBonus> selectMultiShopSharedBonusList(Map<String,Object> map);
}