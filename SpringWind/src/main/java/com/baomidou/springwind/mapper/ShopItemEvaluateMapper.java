package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopItemEvaluate;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.ShopUserEvaluate;
import com.baomidou.springwind.entity.po.PoShopItemEvaluate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 用户点评表，记录点评信息 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopItemEvaluateMapper extends BaseMapper<ShopItemEvaluate> {
    List<ShopItemEvaluate> selectShopItemEvaluate(@Param("user_id") String user_id);

    /**
     * 获取点评商品列表
     * @param map
     * @return
     */
    List<ShopItemEvaluate> selectMultiShopItemEvaluateList(Map<String,Object> map);

    /**
     * 查询点评列表总数
     * @return
     */
    Integer selectMultiShopItemEvaluateCount(Map<String,Object> map);

    int selectCountByuserId(@Param("item_id")String item_id);

    List<String> selectEIds(String item_id);
    //获取评分
   Integer getGrade(@Param("item_id")String item_id);
    //获取评价列表
    List<ShopUserEvaluate> getItemevaluateList(@Param("item_id")String item_id,@Param("page_no") Integer page_no);
   //获取点评列表
    List<ShopItemEvaluate> shopItemEvaluateByuserId(@Param("user_id")String user_id,@Param("page_no") int page_no);

    /**
     * 查询所有未删除的点评
     * @param map
     * @return
     */
    List<PoShopItemEvaluate> selectEvaluateListAll(Map<String,Object> map);

    /**
     * 查询所有未删除点评的数量
     * @param map
     * @return
     */
    Integer selectEvaluateListAllCount(Map<String,Object> map);
}