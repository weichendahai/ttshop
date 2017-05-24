package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.po.PoShopItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 商城商品表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopItemMapper extends BaseMapper<ShopItem> {
    ShopItem selectById1(@Param("item_id") String item_id);
   //商品的模糊搜索(state = 0 ,1)
    List<ShopItem> selectByKeyword(@Param("keyword")String  keyword,@Param("page_no")int page_no,@Param("order_price")int order_price,@Param("order_count")int order_count);
    //商品的模糊搜索(全状态)
    List<ShopItem> selectByKeywordAllState(@Param("keyword")String  keyword,@Param("page_no")int page_no);
    int selectAll(@Param("keyword")String searchText);
   //商品的分类搜索
    int selectAllCategory(@Param("secValues")List<String> secValues);
   //分类的搜索结果展示
    List<ShopItem> selectByCategory(@Param("secValues")List<String> secValues, @Param("page_no") int i);

    /**
     * 查询商品列表总数
     * @param map
     * @return
     */
    Integer selectMultiShopItemCategoryCount(Map<String,Object> map);

    /**
     * 查询商品列表
     * @param map
     * @return
     */
    List<ShopItem> selectMultiShopItemCategoryList(Map<String,Object> map);
   //分类查询条件
    List<ShopItem> selectByKeyword1(@Param("keyword")String seaech1, @Param("category_id")String category_id,@Param("page_no") int page_no,@Param("order_price")int order_price,@Param("order_count")int order_count);
    int selectAll1(@Param("keyword")String seaech1, @Param("category_id")String category_id);
   //搜索结果查询条件
    List<ShopItem> selectByKeyword2(@Param("keyword")String seaech1, @Param("category_id")String category_id, @Param("page_no")int page_no,@Param("order_price")int order_price,@Param("order_count")int order_count);
    int selectAll2(@Param("keyword")String seaech1, @Param("category_id")String category_id);

    /**
     * 添加商品并返回主键
     * @param shopItem
     * @return
     */
    /*Integer insertShopItemBackKey(ShopItem shopItem);*/

    /**
     * 查询商品列表带有一级二级分类名称
     * @param map
     * @return
     */
    List<PoShopItem> selectMultiShopItemList(Map<String,Object> map);
}