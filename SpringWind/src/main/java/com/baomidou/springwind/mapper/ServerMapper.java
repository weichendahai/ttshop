package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 设置首页banner表 Mapper 接口
 * </p>
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ServerMapper extends BaseMapper<ShopUserAddr> {
     /*
     * 获取数据库banner对象
     *@return List
     * */
   List<ShopBanner> getBannerList123();
    //获取爆款列表
    List<ShopHotItem> getHotitemList123();
    List<ShopHotItem> getHotitemList1123();
    //获取商品描述
    List<ShopItemInfo> getItemInfo(@Param("item_id")  String item_id,@Param("evaluate_user_id")  String  evaluate_user_id, @Param("shared_user_id")  String shared_user_id);
    List<ShopItemProperties>  getProps(@Param("item_id")  String item_id,@Param("evaluate_user_id")  String  evaluate_user_id, @Param("shared_user_id")  String shared_user_id);
    List<ShopItemUserDistribution> getDistributions(@Param("item_id")  String item_id,@Param("evaluate_user_id")  String  evaluate_user_id, @Param("shared_user_id")  String shared_user_id);
    List<ShopItemPropertiesValues>  getPvalues(@Param("item_id")  String item_id,@Param("evaluate_user_id")  String  evaluate_user_id, @Param("shared_user_id")  String shared_user_id,  @Param("property_key") String  property_key);
    List<ShopItemUserDistribution>  getDvalues(@Param("item_id")  String item_id,@Param("evaluate_user_id")  String  evaluate_user_id, @Param("shared_user_id")  String shared_user_id,  @Param("distribution_type") String  distribution_type);
   //获取商品详情
   List<String> getItemDesc(@Param("item_id")  String item_id);
    //获取商品评价列表
    List<ShopUserEvaluate> getItemevaluateList(@Param("item_id")  String item_id);
    //获取用户信息
    ShopUser getUserInfo(@Param("user_id" ) String user_id);
    //修改用户基本信息
    int changeNickname( @Param("user_id")String user_id,@Param("new_value") String new_value);
    int changeSkinType( @Param("user_id") int user_id,@Param("new_value") int new_value);
    int changeSex( @Param("user_id")int user_id,@Param("new_value") int new_value);
    int changeDate( @Param("user_id")String user_id,@Param("new_value") String new_value);
   //获取订单详情
    ShopOrder getOrderInfo(@Param("user_id") String user_id,@Param("order_id") String order_id );
    List<Long>  getItemId(@Param("order_id") Long order_id);
     List< ShopUserAddr> getUserAddr(@Param("user_id")String user_id);
     ShopUserAddr getUserAddrById(@Param("AddrId")String AddrId);
    ShopItem getItemById(@Param("ItemsId") Long ItemsId);
    ShopOrderItem getItemOrder(@Param("order_id")Long order_id,@Param("ItemsId") Long ItemsId );
    //获取订单列表
    List<ShopOrder> getOrderList(@Param("user_id") String user_id);
    //申请退款
    void  requestRefund( @Param("uf") ShopUserRefund uf );
    //退款列表
     ShopOrder getOrderById(@Param("order_id") Long order_id);
    List<ShopUserRefund> getreFundList(@Param("user_id") String user_id);
    //获取点评详情
    ShopItemEvaluate getEvaluateInfo(String evaluate_id);
    ShopOrderItem getItemOrderByItemid(Long itemId);
    //修改用户地址
   int   insertAddr(@Param("user_id")String user_id,@Param("contact")String contact ,@Param("mobile_phone")String mobile_phone ,@Param("address")String address);
   void insertAddrO(@Param("addr")ShopUserAddr addr);
    //获取porperties
    ShopItemProperties getPropsById(Long item_id);
    }