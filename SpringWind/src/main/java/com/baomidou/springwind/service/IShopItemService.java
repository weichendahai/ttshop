package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.PoShopItem;
import com.baomidou.springwind.exception.ShopException;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商城商品表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopItemService extends IService<ShopItem> {

    public static final Integer StateCreate = 0;//创建
    public static final Integer StatePutOn = 1;//上架
    public static final Integer StatePutOff= -1;//下架


//获取商品描述
    String getItemInfo(@Param("item_id")  String item_id,@Param("evaluate_user_id")  String  evaluate_user_id, @Param("shared_user_id")  String shared_user_id,String evaluate_shared_id,String user_id);
   //获取商品描述
    String getItemDesc(String item_id);
    //后台向redis添加商品信息
    void addItemToRedis(ShopItem shopItem);
    //后台删除redis的数据
    void delItemRedis(String item_id);
    //修改商品信息
    void updateItem(ShopItem shopItem);

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

   /**
    * 添加商品并返回主键
    * @param shopItem
    * @return
    */
   Integer insertShopItemBackKey(ShopItem shopItem);

    /**
     * 更新商品并返回主键
     * @param shopItem
     * @return
     */
    Integer updateShopItemBackKey(ShopItem shopItem);

    /**
     * 查询商品列表带有一级二级分类名称
     * @param map
     * @return
     */
    List<PoShopItem> selectMultiShopItemList(Map<String,Object> map);

    List<ShopItem> selectItemByKeywordAllState(String keyword);

    /**
     * 通过多个IDS字符串查询多个商品
     * @param itemIds
     * @return
     */
    List<ShopItem> selectItemListByIds(String itemIds);

    /**
     * 上架
     */
    void putItemOn(Integer itemId) throws ShopException;

    /**
     * 上架
     */
    void putItemOn(ShopItem shopItem) throws ShopException;

    /**
     * 下架
     */
    void putItemOff(Integer itemId) throws ShopException;

    /**
     * 下架
     */
    void putItemOff(ShopItem shopItem) throws ShopException;
}
