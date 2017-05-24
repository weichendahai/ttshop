package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopEvaluateShared;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.po.PoShopEvaluateShared;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 点评的分享记录表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopEvaluateSharedMapper extends BaseMapper<ShopEvaluateShared> {
    List<ShopEvaluateShared>  getInfoByUserId(@Param("user_id")String user_id,@Param("page_no")int page_no);
    //获取点评对应的分享
    int getCountShared(@Param("id")String id);

    /**
     * 获取分享列表的条目数
     * @param map
     * @return
     */
    Integer selectMultiShopEvaluateSharedCount(Map<String,Object> map);

    /**
     * 查询分类列表
     * @param map
     * @return
     */
    List<PoShopEvaluateShared> selectMultiShopEvaluateSharedList(Map<String,Object> map);

}