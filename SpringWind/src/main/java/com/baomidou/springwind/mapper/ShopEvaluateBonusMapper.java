package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.ShopEvaluateBonus;
import com.baomidou.springwind.entity.po.PoShopEvaluateBonus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 点评佣金表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopEvaluateBonusMapper extends BaseMapper<ShopEvaluateBonus> {
    List<ShopEvaluateBonus>  ShopEvaluateBonusByuserId(@Param("user_id") String user_id,@Param("page_no")int page_no);
    //获取所有的周榜单数据
    List<ShopEvaluateBonus> selectRank(@Param("startDate")String startDate,@Param("endDate") String endDate,@Param("page_no") int page_no,@Param("str") String str);
   //获取分页的总条数
    Integer getCount(@Param("startDate") String  startDate,@Param("endDate") String  endDate,@Param("str") String str );

    /**
     * 查询点评佣金详细信息
     * @param map
     * @return
     */
    List<PoShopEvaluateBonus> selectMultiShopEvaluateSharedList(Map<String,Object> map);
}