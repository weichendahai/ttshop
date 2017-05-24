package com.baomidou.springwind.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.service.*;
import com.baomidou.springwind.wx.MeizhuangWx;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @author wangzhen
 * @since 2017-03-23
 */
@Controller
@RequestMapping("/server")
public class ServerController extends BaseController{
    @Autowired
    private ServerService serverService;
    @Autowired
    private IShopGiftCardService iShopGiftCardService;
    @Autowired
    private IShopCouponService iShopCouponService;
    @Autowired
    private IShopBonusPaymentService iShopBonusPaymentService;
    @Autowired
    private IShopCartService ishopCartService;
    @Autowired
    private IShopSampleService iShopSampleService;
    @Autowired
    private IShopItemEvaluateService iShopItemEvaluateService;
    @Autowired
    private IShopBannerService  iShopBannerService;
    @Autowired
    private IShopHotItemService iShopHotItemService;
    @Autowired
    private IShopItemService  iShopItemService;
    @Autowired
    private IShopUserService iShopUserService;
    @Autowired
    private IShopOrderItemPropertiesService iShopOrderItemPropertiesService;
    @Autowired
    private IShopOrderService iShopOrderService;
    @Autowired
    private IShopUserRefundService iShopUserRefundService;
    @Autowired
    private IShopSearchHistoryService   iShopSearchHistoryService;
    @Autowired
    private IShopEvaluateSharedService iShopEvaluateSharedService ;
    @Autowired
    private IShopDeliverService iShopDeliverService;
    @Autowired
    private IShopItemCategoryService iShopItemCategoryService;
    @Autowired
    private IShopFeedbackService shopFeedbackService;
    @Autowired
    private IShopEvaluateBonusService shopEvaluateBonusService;
    @Autowired
    private IShopItemUserDistributionService iShopItemUserDistributionService;
    /*
    * 商品模块
    * */
    //获取banner列表1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getbannerlist")
    public @ResponseBody String getBannerList(String jsonpCallback) {
        String str=iShopBannerService.getBannerList();
            return jsonpCallback+"("+str+");";
    }
    //获取爆款列表12
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/gethotitemlist")
    public @ResponseBody String getHotitemList(String jsonpCallback,Long page_no,String  season_no) {
        //String str= serverService.getHotitemList(page_no ,season_no);
        String str=iShopHotItemService.getHotitemList(page_no, season_no);
        return jsonpCallback+"("+str+");";
    }
    //获取商品描述1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getiteminfo")
   /* , String item_id,String evaluate_user_id,String shared_user_id*/
    public @ResponseBody String getItemInfo(String jsonpCallback,@Param("item_id")  String item_id,@Param("evaluate_user_id")  String  evaluate_user_id, @Param("shared_user_id")  String shared_user_id,String evaluate_shared_id,String user_id) {
       // String str= serverService.getItemInfo(item_id,evaluate_user_id,shared_user_id);
        String str=iShopItemService.getItemInfo(item_id, evaluate_user_id, shared_user_id,evaluate_shared_id,user_id);
        return jsonpCallback+"("+str+");";
    }
    //获取商品详情1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getitemdesc")
    public @ResponseBody String getItemDesc(String jsonpCallback,@Param("item_id") String item_id) {
        //String str= serverService.getItemDesc(item_id);
        String str=iShopItemService.getItemDesc(item_id);
        return jsonpCallback+"("+str+");";
    }


    //获取商品评价列表1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getitemevaluatelist")
    public @ResponseBody String getItemevaluateList(String jsonpCallback,@Param("item_id") String item_id,String page_no) {
      //  String str= serverService.getItemevaluateList(item_id);
        String str= iShopItemEvaluateService.getItemevaluateList(item_id,page_no);
        return jsonpCallback+"("+str+");";
    }

    /*
       * 用户模块
       * */
    //获取用户信息1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getuserinfo")
    public @ResponseBody String getUserInfo(String jsonpCallback, @Param("user_id" ) String user_id) {
      //  String str= serverService.getUserInfo(user_id);
        String str= iShopUserService.getUserInfo(user_id);
        return jsonpCallback+"("+str+");";
    }
    /*
    * @param user_id  img_data
    * */
    //修改用户头像
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/changeuserhead")
    public @ResponseBody String changeUserHead(String jsonpCallback, String user_id,String img_data) {
        String str= serverService.changeUserHead(user_id, img_data);
        return jsonpCallback+"("+str+");";
    }
    //修改用户基本信息1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/changeuserinfo")
    public @ResponseBody String changeUserInfo(String jsonpCallback, @Param("user_id")String user_id,@Param("info_type") int info_type,@Param("new_value") String new_value) {
        try {
            new_value=URLDecoder.decode(new_value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //String str= serveService.changeUserInfo(user_id,info_type,new_value);
          String str= iShopUserService.changeUserInfo(user_id,info_type,new_value);
        return jsonpCallback+"("+str+");";
    }
    //请求手机验证码
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/sendsmscode")
    public @ResponseBody String sendSmsCode(String jsonpCallback, String user_id) {
        String str= serverService.sendSmsCode(user_id);
        return jsonpCallback+"("+str+");";
    }
    //修改手机号
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/changemobilenumber")
    public @ResponseBody String changeMobileNumber(String jsonpCallback, String user_id,String sms_code,String new_mobile_number) {
        String str= serverService.changeMobileNumber(user_id,sms_code,new_mobile_number);
        return jsonpCallback+"("+str+");";
    }

    //获取订单列表12
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getorderlist")
    public @ResponseBody String getOrderList (String jsonpCallback, @Param("user_id") String user_id,@Param("page_no") String page_no,@Param("order_state") String order_state) {
       // String str= serverService.getOrderList(user_id, page_no, order_state);
          String str= iShopOrderService.getOrderList(user_id, page_no, order_state);
        return jsonpCallback+"("+str+");";
    }
    //获取订单详情1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getorderinfo")
    public @ResponseBody String getOrderInfo (String jsonpCallback, @Param("user_id") String user_id, @Param("order_id")String order_id ) {
       // String str= serverService.getOrderInfo(user_id, order_id);
        String str= iShopOrderItemPropertiesService.getOrderInfo(user_id, order_id);
        return jsonpCallback+"("+str+");";
    }
    //申请退款1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/requestrefund")
    public @ResponseBody String requestRefund(String jsonpCallback,@Param("user_id") String user_id,@Param("order_id")String order_id,@Param("item_id")String item_id,@Param("refund_type")String refund_type ,@Param("refund_reason")String refund_reason,@Param("refund_info")String refund_info ,@Param("refund_image_addr")String refund_image_addr,String order_item_id) {
        try {
            String   refund_infos="";
            if ("".equals(refund_info)||refund_info==null){

            }else {
          refund_infos =URLDecoder.decode(refund_info, "utf-8");
            }
      //  String str= serverService.requestRefund(user_id, order_id,item_id,refund_type,refund_reason,refund_info,refund_image_addr);
        String str= iShopUserRefundService.requestRefund(user_id, order_id,item_id,refund_type,refund_reason,refund_infos,refund_image_addr,order_item_id);
        return jsonpCallback+"("+str+");";
        } catch (Exception e) {
            e.printStackTrace();
            return jsonpCallback;
        }
    }
   //获取退款记录列表2
   @Login(action = Action.Skip)
   @Permission(action = Action.Skip)
   @RequestMapping("/getrefundlist")
   public @ResponseBody String getreFundList(String jsonpCallback, @Param("user_id")String user_id,@Param("page_no")String page_no) {
       String str= iShopUserRefundService.getreFundList(user_id, page_no);
       return jsonpCallback+"("+str+");";
   }
    //获取点评记录列表12
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getuserevaluatelist")
    public @ResponseBody String getUserevaluateList(String jsonpCallback, @Param("user_id") String user_id,@Param("page_no")String page_no ,@Param("list_type")String list_type,@Param("only_bonus")String only_bonus) {
        String str= iShopItemEvaluateService.getUserevaluateList(user_id, page_no,list_type,only_bonus);
        return jsonpCallback+"("+str+");";
    }
    //获取点评详情1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getevaluateinfo")
    public @ResponseBody String getEvaluateInfo(String jsonpCallback,@Param("user_id") String user_id,@Param("evaluate_id")String evaluate_id,String evaluate_shared_id )
    {
     //  String str= serverService.getEvaluateInfo(user_id, evaluate_id);
        String str= iShopItemEvaluateService.getEvaluateInfo(user_id, evaluate_id,evaluate_shared_id);
        return jsonpCallback + "("+str+");";
    }
    //获取地址列表1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getaddresslist")
    public @ResponseBody String getAddressList(String jsonpCallback, @Param("user_id")String user_id) {
        String str= serverService.getAddressList(user_id);
        return jsonpCallback + "("+str+");";
    }
    //新增收货地址1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/addaddress")
    public @ResponseBody String addAddress(String jsonpCallback, @Param("user_id")String user_id,@Param("contact")String contact ,@Param("mobile_phone")String mobile_phone ,@Param("address")String address,String defaults) {
        try {
            address=URLDecoder.decode(address, "utf-8");
            contact=URLDecoder.decode(contact, "utf-8");
            mobile_phone=URLDecoder.decode(mobile_phone, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str= serverService.addAddress(user_id,contact,mobile_phone,address,defaults);
        return jsonpCallback + "("+str+");";
    }
    //修改收货地址1
      @Login(action = Action.Skip)
      @Permission(action = Action.Skip)
      @RequestMapping("/editaddress")
      public @ResponseBody String editAddress(String jsonpCallback, @Param("user_id")String user_id,@Param("contact")String contact ,@Param("mobile_phone")String mobile_phone ,@Param("address")String address,@Param("address_id")String address_id,String defaults ) {
          try {
              address=URLDecoder.decode(address, "utf-8");
              contact=URLDecoder.decode(contact, "utf-8");
              mobile_phone=URLDecoder.decode(mobile_phone, "utf-8");
          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }
          String str= serverService.editAddress(user_id, contact, mobile_phone, address,address_id,defaults);
        return jsonpCallback + "("+str+");";
    }
    //删除收货地址1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/removeaddress")
    public @ResponseBody String removeAddress(String jsonpCallback, String user_id, String address_id ) {
        String str= serverService.removeAddress(user_id, address_id);
        return jsonpCallback + "(" + str + ");";
    }
    //获取红包12
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getgiftcard")
    public @ResponseBody String getGiftCard(String jsonpCallback, String user_id, String page_no,String valid) {
        String str= iShopGiftCardService.getGiftCard(user_id, page_no,valid);
        return jsonpCallback + "(" + str + ");";
    }
    //获取优惠券12
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getcoupon")
    public @ResponseBody String getCoupon(String jsonpCallback, String user_id, String page_no ,String valid) {
        String str= iShopCouponService.getCoupon(user_id, page_no,valid);
        return jsonpCallback + "(" + str + ");";
    }
    //获取佣金及体现信息12
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getbonus")
    public @ResponseBody String getBonus(String jsonpCallback, String user_id, String page_no ) {
        String str= iShopBonusPaymentService.getBonus(user_id, page_no);
        return jsonpCallback + "(" + str + ");";
    }
    //申请体现1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/requestbonuspayment")
    public @ResponseBody String requestBonusPayment(String jsonpCallback, String user_id) {
        String str= iShopBonusPaymentService.requestBonusPayment(user_id);
        return jsonpCallback + "(" + str + ");";
    }
    /*
  * 购买模块
  * */
    //加入购物车1
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/additemtocart")
    public @ResponseBody String addItemtoCart(String jsonpCallback, String user_id,String item_id,String evaluate_shared_id,String property_key ,String property_value ) {
        //物品放入购物车
        String str= ishopCartService.addItemtoCart(user_id,item_id,evaluate_shared_id,property_key,property_value);
        return jsonpCallback+"("+str+");";
    }
  //获取购物车数据
   @Login(action = Action.Skip)
   @Permission(action = Action.Skip)
   @RequestMapping("/getcartitems")
   public @ResponseBody String getCartItems(String jsonpCallback, String user_id){
       String str=ishopCartService.getCartItems(user_id);
       return jsonpCallback + "(" + str + ");";
   }
    //修改购物车数量
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/modifycartitem")
    public @ResponseBody String modifyCartItem(String jsonpCallback, String user_id,String item_count,String cart_item_id){
        String str=ishopCartService.modifyCartItem(user_id,item_count,cart_item_id);
        return jsonpCallback + "(" + str+ ");";
    }
    /*
    * 页面暂停计时器*/
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/stopcartcd")
    public @ResponseBody String stopCartCd(String jsonpCallback, String user_id,String cart_item_ids ){
        try {
            cart_item_ids=URLDecoder.decode(cart_item_ids,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str=ishopCartService.stopCartCd(user_id,cart_item_ids);
        return jsonpCallback + "(" + str+ ");";
    }
    /*
    * 页面开始计时
    * */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/resumecartcd")
    public @ResponseBody String resumeCartcd(String jsonpCallback, String user_id ){
        String str=ishopCartService.resumeCartcd(user_id);
        return jsonpCallback + "(" + str+ ");";
    } /*
* 提交订单*/
@Login(action = Action.Skip)
@Permission(action = Action.Skip)
@RequestMapping("/submitorder")
public @ResponseBody String submitOrder(String jsonpCallback, String user_id,String order_addr_id, String user_gift_card_id,String user_coupon_id){
    String str=ishopCartService.submitOrder(user_id,order_addr_id,user_coupon_id,user_gift_card_id);
    return jsonpCallback + "(" + str+ ");";
}
    /*
    * 试用列表展示
    * */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getsamplelist")
    public @ResponseBody String getSampleList(String jsonpCallback,String user_id,String page_no,String list_type ){
        String str=iShopSampleService.getSampleList(user_id, page_no, list_type);
        return jsonpCallback + "(" + str+ ");";
    }
    /*
    * 申请试用
    * */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/applysample")
    public @ResponseBody String applySample(String jsonpCallback,String user_id,String sample_id ){
        String str=iShopSampleService.applySample(user_id, sample_id);
        return jsonpCallback + "(" + str+ ");";
    }
    /*
    * 点评模块
    * */
   //写点评
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/addevaluate")
    public @ResponseBody String addEvaluate(String jsonpCallback,String user_id,String item_id,String item_advantage, String item_disadvatage,String evaluate_image_addr,String evaluate_vedio_addr,String grade,String other_item_ids){
       try {
           item_advantage= URLDecoder.decode(item_advantage, "utf-8");
           item_disadvatage = URLDecoder.decode(item_disadvatage, "utf-8");
       }catch (Exception e){
           e.printStackTrace();
       }
           String str=iShopItemEvaluateService.addEvaluate(user_id,item_id,item_advantage,item_disadvatage,evaluate_image_addr,evaluate_vedio_addr,grade,other_item_ids);
           return jsonpCallback + "(" + str+ ");";
        /*String str=iShopItemEvaluateService.addEvaluate(user_id,item_id,item_advantage,item_disadvatage,evaluate_image_addr,evaluate_vedio_addr,grade,other_item_id,other_item_name,other_item_icon);
        return jsonpCallback + "(" + str+ ");";*/
    }
/*
* 其他模块
* */
/*
@Login(action = Action.Skip)
@Permission(action = Action.Skip)
@RequestMapping("/addbanner")
public @ResponseBody String addBanner(String jsonpCallback,String user_id,String item_id,String item_advantage, String item_disadvatage,String evaluate_image_addr,String evaluate_vedio_addr,String grade,String other_item_id,String other_item_name,String other_item_icon){
    String str=iShopItemEvaluateService.addEvaluate(user_id,item_id,item_advantage,item_disadvatage,evaluate_image_addr,evaluate_vedio_addr,grade,other_item_id,other_item_name,other_item_icon);
    return jsonpCallback + "(" + str+ ");";
}
*/
    /*搜索模块
    * */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/searchprepare")
    public @ResponseBody String searchPrepare(String jsonpCallback,String user_id){
        String str=iShopSearchHistoryService.searchPrepare(user_id);
        return jsonpCallback + "(" + str+ ");";
    }
   //搜索功能
   @Login(action = Action.Skip)
   @Permission(action = Action.Skip)
   @RequestMapping("/search")
   public @ResponseBody String search(String jsonpCallback,String user_id,String keyword,String page_no,String category_id,String order_userful,String order_count){
       String  keywords= null;
       try {
            keywords = URLDecoder.decode(keyword, "utf-8");
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }
       String str=iShopSearchHistoryService.search(user_id,keywords,page_no,category_id, order_userful,order_count);
       return jsonpCallback + "(" + str+ ");";
   }
   //获取点评和分享榜单
   @Login(action = Action.Skip)
   @Permission(action = Action.Skip)
   @RequestMapping("/evaluateranking")
   public @ResponseBody String evaluateRanking(String jsonpCallback,String ranking_type,String cycle,String season_no,String user_id,String page_no){
        //String str=iShopSearchHistoryService.search(user_id,keywords,page_no);
        String str=iShopEvaluateSharedService.evaluateRanking(ranking_type,cycle,season_no,user_id,page_no);
        return jsonpCallback + "(" + str+ ");";
    }
    //立即购买
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/buynow")
    public @ResponseBody String buyNow(String jsonpCallback,String cart_item_ids,String user_id,String evaluate_shared_id){
        //String str=iShopSearchHistoryService.search(user_id,keywords,page_no);
        String str=ishopCartService.buyNow(cart_item_ids, user_id, evaluate_shared_id);
        return jsonpCallback + "(" + str+ ");";
    }
    /*微信接口*/
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getwxinfo")
    public @ResponseBody String getWxInfo(String jsonpCallback,String code){
        //String str=iShopSearchHistoryService.search(user_id,keywords,page_no);
        String str=ishopCartService.isUserLogin(code);
        return jsonpCallback + "(" + str+ ");";
    }
    //转发朋友圈
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/sharedevaluate")
    public @ResponseBody String sharedEvaluate(String jsonpCallback,String evaluate_id,String user_id){
        //String str=iShopSearchHistoryService.search(user_id,keywords,page_no);
        String str=iShopEvaluateSharedService.sharedEvaluate(evaluate_id,user_id);
        return jsonpCallback + "(" + str+ ");";
    }
    //获取微信签名
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getwxsdk")
    public @ResponseBody String getWxSdk(String jsonpCallback,String wxurl){
        //String str=iShopSearchHistoryService.search(user_id,keywords,page_no);
        String str=ishopCartService.getWxSdk(wxurl);
        return jsonpCallback +"("+str+");";
    }
    //微信提现
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getwxbounds")
    public @ResponseBody String getwxBounds(String jsonpCallback){
        //String str=iShopSearchHistoryService.search(user_id,keywords,page_no);
        String str="";/*ishopCartService.getwxBounds();*/
        return jsonpCallback +"("+str+");";
    }
    /*快递接口
    * 处理未发货发货查询退货查询的接口*/
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getdeliveryorders")
    public @ResponseBody String getDeliveryOrders(String auth_id){
        String str;
        if ("".equals(auth_id)||auth_id==null){
        str= "访问认证错误,请向管理员获得认证码";
            return "(" + str+ ");";
        }else {
        //String str=iShopSearchHistoryService.search(user_id,keywords,page_no);
        str=iShopDeliverService.getDeliveryOrders(auth_id);
            return "(" + str+ ");";
        }
    }
    //发货操作
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/orderdeliver")
    public @ResponseBody String orderdeliver(String order_id,String express_code){
        //String str=iShopSearchHistoryService.search(user_id,keywords,page_no);
        String str=iShopDeliverService.orderdeliver(order_id,express_code);
        return "(" + str+ ");";
    }
    //退货返还操作
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getuserrefunds")
    public @ResponseBody String getUserreFunds(String auth_id ){
        //String str=iShopSearchHistoryService.search(user_id,keywords,page_no);
        String str;
        if ("".equals(auth_id)||auth_id==null){
            str= "访问认证错误,请向管理员获得认证码";
            return  "(" + str+ ");";
        }else {
            //String str=iShopSearchHistoryService.search(user_id,keywords,page_no);
            str=iShopDeliverService.getUserreFunds(auth_id);
            return "(" + str+ ");";
        }
    }
    // 退货操作
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/refundfeedback")
    public @ResponseBody String refundFeedBack (String refund_id,String type,String message){
        String str=iShopDeliverService.refundFeedBack(refund_id,type,message);
        return "(" + str+ ");";
    }
    //微信支付
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/payforwx")
    @ResponseBody
    public String payForwx (String jsonpCallback,String order_id,HttpServletRequest request,String wxurl){
        String ip=  request.getRemoteAddr();
        String str=ishopCartService.payForWx(order_id,ip,wxurl);
        return jsonpCallback + "(" + str+ ");";
    }
    //微信支付
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/paysuccess")
    public @ResponseBody String paySuccess (String jsonpCallback,String order_id,String success,String trans_number){
        String str=ishopCartService.paySuccess(order_id, success,trans_number);
        return jsonpCallback +"("+str+");";
    }
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/apppaynotify")
    public @ResponseBody String appPayNotify (HttpServletRequest request){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        ServletInputStream in = null;
        try {
          in = request.getInputStream();
            String xmlMsg = MeizhuangWx.inputStream2String(in);
            Map<String, String> map = MeizhuangWx.doXMLParse(xmlMsg);
            String trans_number = map.get("transaction_id");
            String order_id = map.get("out_trade_no");
            String success=map.get("return_code");
            if (StringUtils.equalsIgnoreCase(success,"SUCCESS")){
                String str=ishopCartService.paySuccess(order_id, success, trans_number);
            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取流异常");
        }

        return null;
    }
 /* @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/pitchUser")
    public void  setPitchUser(){
       iShopUserService. setPitchUser();
    }*/
    /**
     * 获取反馈意见列表
     */
  @Login(action = Action.Skip)
 @Permission(action = Action.Skip)
 @RequestMapping("/getfeedback")
 public @ResponseBody String getFeedBack (String jsonpCallback,String user_id,String page_no){
     String str=shopFeedbackService.getFeedBack(user_id,page_no);
     return jsonpCallback +"("+str+");";
 }
    /**
     * 提交反馈意见
     */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/submitfeedback")
    public @ResponseBody String submitFeedBack (String jsonpCallback,String  user_id,String  fid, String  img_addr	,String  content){
        String str= null;
        try {
            if (content ==null||content.length()==0){
                str="没有反馈内容商城无法为您最缺回复";
            }else {
            str = shopFeedbackService.submitFeedBack(user_id,fid,img_addr, URLDecoder.decode(content, "utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("提交反馈异常");
        }
        return jsonpCallback +"("+str+");";
    }
    /**
     * 阅读反馈意见
    */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/readfeedback")
    public @ResponseBody String readFeedBack (String jsonpCallback,String user_id,String id){
        String str=shopFeedbackService.readFeedBack(user_id, id);
        return jsonpCallback +"("+str+");";
    }
    /**
     * 阅读反馈意见
     */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getbounds")
    public @ResponseBody String getBounds (String jsonpCallback,String order_id){
        shopEvaluateBonusService.getShopEvaluateBonus(order_id);
        return jsonpCallback +"("+1112222+");";
    }
    /*取消订单*/
   @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/cancelorder")
    public @ResponseBody String cancelOrder (String jsonpCallback,String order_id,String user_id){
       String str= iShopOrderService.cancelOrder(order_id,user_id);
       return jsonpCallback +"("+str+");";
    }
 }
