package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.ShopSample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 试用表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopSampleMapper extends BaseMapper<ShopSample> {

    List<ShopSample> selectAll(@Param("user_id") String user_id ,@Param("page_no") int page_no);

    /**
     * 获取试用列表
     * @param map
     * @return
     */
    List<ShopSample> selectMultiShopSampleList(Map<String,Object> map);

    /**
     * 获取列表的数量
     * @param map
     * @return
     */
    Integer selectMultiShopSampleCount(Map<String,Object> map);
   //查询所有的列表总数
    int selectAllCount(@Param("user_id")String user_id);

    /**
     * 查询未试用的商品名称
     * @param map
     * @return
     */
    List<ShopSample> selectItemNameList(Map<String,Object> map);

    /**
     * 根据商品名称和一级分类名称和二级分类名称查询未试用商品的名称
     * @param map
     * @return
     */
    List<ShopSample> selectMultiShopSampleItemNameList(Map<String,Object> map);

    /**
     * 根据商品名称和一级分类名称和二级分类名称查询未试用商品的名称的数量
     * @param map
     * @return
     */
    Integer selectMultiShopSampleItemNameCount(Map<String,Object> map);
    //全随机获取x个用户
    String[] getSampleIds();
}