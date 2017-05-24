package com.baomidou.springwind.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.ShopUserAddr;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ServerService extends IService<ShopUserAddr> {
    //获取爆款列表
    String   getHotitemList( int page_no,int season_no);
    //获取商品描述
    String   getItemInfo(@Param("item_id")  String item_id,@Param("evaluate_user_id")  String  evaluate_user_id, @Param("shared_user_id")  String shared_user_id);
    //获取商品评价列表
    String getItemevaluateList(@Param("item_id") String item_id);
    //获取详情列表
   String getItemDesc(@Param("item_id") String item_id );
    //加入到购物车
   String addItemtoCart( @Param("user_id" )String user_id,@Param("item_id" ) String item_id);
    //获取用户信息
    String getUserInfo( @Param("user_id" )String user_id);
    //获取用户头像
    String changeUserHead(@Param("user_id")String user_id,@Param("img_data")String img_data);
    //修改用户基本信息
    String changeUserInfo( @Param("user_id")String user_id,@Param("info_type")int info_type,@Param("new_value") String new_value);
    //获取手机验证码
    String sendSmsCode( String user_id);
    //修改手机号
    String changeMobileNumber(String user_id,String sms_code,String new_mobile_number);
    //订单装态
    String getOrderList ( @Param("user_id") String user_id,@Param("page_no")String page_no,@Param("order_state") String order_state);
    //订单详情
    String  getOrderInfo (  @Param("user_id")String user_id, @Param("order_id")String order_id  );
    //申请退款
    String requestRefund( @Param("user_id") String user_id,@Param("order_id")String order_id,@Param("item_id")String item_id,@Param("refund_type")String refund_type ,@Param("refund_reason")String refund_reason,@Param("refund_info")String refund_info ,@Param("refund_image_addr")String refund_image_addr );
    //获取退款记录列表
    String getreFundList( @Param("user_id")  String user_id, @Param("page_no") String page_no);
    //获取点评记录列表
    String getUserevaluateList( @Param("user_id")String user_id,@Param("page_no")String page_no ,@Param("list_type")String list_type,@Param("only_bonus")String only_bonus);
    //获取点评详情getEvaluateInfo
    String getEvaluateInfo( @Param("user_id")String user_id,@Param("evaluate_id")String evaluate_id);
    //获取地址列表
    String getAddressList( @Param("user_id")String user_id);
    //新增收货地址
    String addAddress(@Param("user_id") String user_id,@Param("contact")String contact ,@Param("mobile_phone")String mobile_phone ,@Param("address")String address,String defaults);
    //新增收货地址
    String editAddress( @Param("user_id")String user_id,@Param("contact")String contact ,@Param("mobile_phone")String mobile_phone ,@Param("address")String address,@Param("address_id")String address_id ,String defaults);
    //删除收货地址
    String removeAddress( String user_id, String address_id );
    //获取红包
    String getGiftCard( String user_id, String page_no );
    //获取优惠券
    String getCoupon( String user_id, String page_no );
    //获取佣金及体现信息
    String getBonus( String user_id, String page_no );
    //申请体现
    String requestBonusPayment( String user_id);
    }
