package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopUserPromo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 用户拥有的优惠券红包表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopUserPromoMapper extends BaseMapper<ShopUserPromo> {
    //获取红包信息
    List<ShopUserPromo> getShopUserPromo(@Param("user_id") String user_id,@Param("state") int state,@Param("page_no")int page_no);
    List<ShopUserPromo> getShopUserPromoByType(@Param("user_id") String user_id,@Param("state") int state,@Param("promo_type")int promo_type );

    List<ShopUserPromo> selectMulitShopUserPromoList(@Param("_index") Integer _index,@Param("_size") Integer _size);

    Integer selectMulitShopUserPromoListTotal();
 //红包
    List<ShopUserPromo> getShopUserPromoByT(@Param("user_id")String user_id, @Param("state")int states, @Param("promo_type")String promo_type,  @Param("page_no")int page_no);
}