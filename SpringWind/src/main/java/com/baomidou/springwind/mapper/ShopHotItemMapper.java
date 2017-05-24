package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.ShopHotItem;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
  * 爆款商品，大多数都是商品的冗余字段，应该存放在Redis中 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
public interface ShopHotItemMapper extends BaseMapper<ShopHotItem> {

    //查询上一期爆款列表
    List<ShopHotItem> selectShopHotItemLimit(@Param("_index") Integer _index,@Param("_size") Integer _size);

    //更新爆款列表
    List<ShopHotItem> selectUpdateShopHotItemList(@Param("orderCreateDate") Date orderCreateDate);

    Integer getMaxSessionNo();

    List<ShopHotItem> getMaxSessionNoHotItem();

    boolean delShopHotItemByIdAndSessionNo(@Param("id") Integer id,@Param("sessionNo") Integer sessionNo);

    List<ShopHotItem> selectHotItemSessionNoAndDate();
  //获取当前最大期数
    String selectMaxSeaNo();

    /**
     *统计查询爆款商品列表
     * @param map
     * @return
     */
    List<ShopHotItem> selectStatisticShopHotItemList(Map<String,Object> map);

    /**
     * 获取某一期爆款的最大创建时间
     * @param sessionNo
     * @return
     */
    Date selectMaxCreateDate(@Param("sessionNo")Integer sessionNo);
}