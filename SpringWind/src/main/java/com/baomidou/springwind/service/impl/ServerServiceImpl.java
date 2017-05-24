package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.ServerMapper;
import com.baomidou.springwind.mapper.ShopUserAddrMapper;
import com.baomidou.springwind.mapper.ShopUserMapper;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.service.ServerService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设置首页banner表 服务实现类
 * </p>
 * @author wangzhen
 * @since 2017-03-23
 */
@Service
public class ServerServiceImpl extends BaseServiceImpl<ServerMapper,ShopUserAddr> implements ServerService  {
    @Autowired
    private ServerMapper serverMapper ;
    @Autowired
    private ShopUserMapper shopUserMapper;
    @Autowired
    private ShopUserAddrMapper shopUserAddrMapper;
    //获取爆款列表
    /**
     * @return String
     */
    @Override
    public String getHotitemList(int page_no, int season_no ) {
        HashMap param = new HashMap<String, Object>();
        Map content = new HashMap<String, Object>();
        JSONArray json= new JSONArray();
        try {
        //将查询出来的list集合返回放入
            if(season_no >= 0){
           List<ShopHotItem> hotitemlist = serverMapper.getHotitemList123();
                Page<ShopHotItem> pages=new Page<ShopHotItem>();
                pages.setCurrent(1);
                pages.setSize(4);
                pages.setRecords(hotitemlist);
                param.put("result", "0");
                content.put("next_page_no",page_no+1);
                for (ShopHotItem shopHotItem :hotitemlist){
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("item_id", shopHotItem.getItemId());
                    jsonObj.put("item_name",shopHotItem.getItemName());
                    jsonObj.put("item_icon_addr",shopHotItem.getItemIconAddr());
                    jsonObj.put("evaluate_count",shopHotItem.getEvaluateCount());
                    jsonObj.put("grade",shopHotItem.getGrade());
                    jsonObj.put("price",shopHotItem.getPrice());
                    jsonObj.put("advantage",shopHotItem.getAdvantage());
                    jsonObj.put("season_no","123");
                    json.add(jsonObj);
                }
                content.put("hot_items",json);
                param.put("content",content);
                String jsonString = JSON.toJSONString(param);
                return jsonString;
            }else{
                //<0时需要找出期数最大的那个
                List<ShopHotItem> hotitemlist = serverMapper.getHotitemList123();
                Page<ShopHotItem> pages=new Page<ShopHotItem>();
                pages.setCurrent(1);
                pages.setSize(4);
                pages.setRecords(hotitemlist);
                param.put("result", "0");
                content.put("next_page_no",page_no+1);
                for (ShopHotItem shopHotItem :hotitemlist){
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("item_id", shopHotItem.getItemId());
                    jsonObj.put("item_name",shopHotItem.getItemName());
                    jsonObj.put("item_icon_addr",shopHotItem.getItemIconAddr());
                    jsonObj.put("evaluate_count",shopHotItem.getEvaluateCount());
                    jsonObj.put("grade",shopHotItem.getGrade());
                    jsonObj.put("price",shopHotItem.getPrice());
                    jsonObj.put("advantage",shopHotItem.getAdvantage());
                    jsonObj.put("season_no","123");
                    json.add(jsonObj);
                }
                content.put("hot_items",json);
                param.put("content",content);
                String jsonString = JSON.toJSONString(param);
                return jsonString;
        }
        }catch(Exception e) {
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
    //获取商品描述
    @Override
    public String getItemInfo(@Param("item_id")  String item_id,@Param("evaluate_user_id")  String  evaluate_user_id, @Param("shared_user_id")  String shared_user_id) {
        JSONObject param = new JSONObject();
        try {
            List<ShopItemInfo> ItemInfos = serverMapper.getItemInfo(item_id,evaluate_user_id,shared_user_id);
           Map content=new HashMap<String,Object>();
            JSONArray json= new JSONArray();
            JSONArray json1= new JSONArray();
            JSONArray json2= new JSONArray();
            JSONArray json3= new JSONArray();
            JSONArray json4= new JSONArray();
            param.put("result","0");
            for (ShopItemInfo info :ItemInfos){
                JSONObject jsonObj = new JSONObject();
                JSONObject propertys = new JSONObject();
                JSONObject disc = new JSONObject();
                JSONObject Dvalues = new JSONObject();
                JSONObject Pvalues = new JSONObject();
                jsonObj.put("evaluate_user_id",info.getEvaluateUserId());
                jsonObj.put("shared_user_id", info.getSharedUserId());
                jsonObj.put("item_id",info.getId());
                jsonObj.put("item_name",info.getItemName());
                jsonObj.put("item_big_image_addr",info.getItemBigImageAddr());
                jsonObj.put("order_count",info.getOrderCount());
                jsonObj.put("price",info.getPrice());
                //获取props
                List<ShopItemProperties> props  = serverMapper.getProps(item_id, evaluate_user_id, shared_user_id);
                for(ShopItemProperties prop:props){
                    propertys.put("property_key", prop.getPropertyKey());
                 //根据property_key获取 pvalues
                    List<ShopItemPropertiesValues>  pvalues  = serverMapper.getPvalues(item_id, evaluate_user_id, shared_user_id, prop.getPropertyKey().toString());
                    for ( ShopItemPropertiesValues value:pvalues ){
                        Pvalues.put("property_value",value.getPropertyValue());
                        Pvalues.put("property_desc",value.getPropertyDesc());
                        json1.add(Pvalues);
                    }
                    propertys.put("values",json1);
                    json2.add(propertys);
                }

                jsonObj.put("propertys",json2);
                jsonObj.put("grade",info.getGrade());
                jsonObj.put("evaluate_count",info.getEvaluateCount());
                jsonObj.put("shared_count",info.getSharedCount());
                jsonObj.put("item_effect",info.getItemDesc());
                //获取distributions
                List<ShopItemUserDistribution> distributions  = serverMapper.getDistributions(item_id, evaluate_user_id, shared_user_id);
                for ( ShopItemUserDistribution distribution:distributions){
                    propertys.put("distribution_type",distribution.getType());
                    //根据描述获取对应的Dvalues
                    List<ShopItemUserDistribution>  dValues  = serverMapper.getDvalues(item_id, evaluate_user_id, shared_user_id,distribution.getType().toString());
                    for ( ShopItemUserDistribution dValue:dValues ){
                        Dvalues.put("distribution_sub",dValue.getSub());
                        Dvalues.put("distribution_count",dValue.getCount());
                        json4.add(Dvalues);
                    }
                    disc.put("distribution_values",json4);
                    json3.add(disc);
                }
                jsonObj.put("distributions",json3);
                json.add(jsonObj);
            }
            param.put("content",json);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result", "1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
    //获取商品详情
    @Override
    public String getItemDesc(@Param("item_id") String item_id) {
        JSONObject param = new JSONObject();
        try {
            Map content=new HashMap<String,Object>();
            JSONArray json= new JSONArray();
            JSONObject jsonObject=new JSONObject();
            List<String> ItemDescAddrs = serverMapper.getItemDesc(item_id);
            param.put("","0");
             content.put("item_id",item_id);
            for (String ItemDesc:ItemDescAddrs){
                json.add(ItemDesc);
            }
            content.put("item_info_addrs",json);
            param.put("content",content);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
//获取评价表
    @Override
    public String getItemevaluateList(@Param("item_id") String item_id) {
        JSONObject param = new JSONObject();
        JSONObject content = new JSONObject();
        JSONObject evaluate = new JSONObject();
        JSONArray jsonArray =  new JSONArray();
       try {
           param.put("result","0" );
           List<ShopUserEvaluate> valuatelists  = serverMapper.getItemevaluateList(item_id);
           content.put("item_id",item_id);
           for (ShopUserEvaluate valuatelist:valuatelists){
               evaluate.put("user_id",valuatelist.getUserId());
               evaluate.put("evaluate_id",valuatelist.getEvaluateId());
               evaluate.put("user_head_image_addr",valuatelist.getUserHeadImageAdd());
               evaluate.put("user_nickname",valuatelist.getUserNickname());
               evaluate.put("user_evaluate_count",valuatelist.getUserEvaluateCoun());
               evaluate.put("user_shared_count",valuatelist.getUserSharedCount());
               evaluate.put("user_date_of_birth",valuatelist.getUserDateOfBirth());
               evaluate.put("grade",valuatelist.getGrade());
               evaluate.put("item_advantage",valuatelist.getItemAdvantage());
               evaluate.put("item_disadvatage",valuatelist.getItemDisadvatage());
               evaluate.put("create_date", valuatelist.getCreateDate());
               String str= JSON.toJSONString(evaluate, SerializerFeature.DisableCircularReferenceDetect);
               jsonArray.add(JSON.parseObject(str));
           }
           content.put("evaluates",jsonArray);
           param.put("content",content);
           String jsonString = JSON.toJSONString(param);
           return jsonString;
       }catch (Exception e){
           e.printStackTrace();
           param.put("","1");
           String jsonString = JSON.toJSONString(param);
           return jsonString;
       }
    }
    //购物车
    /*加入到购物车*/
    @Override
    public String addItemtoCart(@Param("user_id" )String user_id, @Param("item_id") String item_id) {
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        try {
            param.put("result","0");
            //实现redis缓存的读取
           /* CacheConfModel cModel=new CacheConfModel();
            CacheMgr cm=CacheMgr.getInstance();
            Date date=new Date();
            cModel.setBeginTime(date.getTime());
            cModel.setDurableTime(20 * 1000);
            cModel.setForever(true);
            cm.addCache("user_id", user_id, cModel);
            cm.addCache("item_id",item_id,cModel);
            cm.addCache("user_id",user_id,cModel);*/
            content.put("item_id",item_id);
            SimpleDateFormat f =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            Date d  =  new Date(f.parse( f.format(date)).getTime()+20*60*1000);
            content.put("end_time", f.format(d) );
            param.put("content",content);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
   /*
   *用户模块
    *  */
    //获取用户信息
    @Override
    public String getUserInfo(@Param("user_id" )String user_id) {
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
       try {
           param.put("result","0");
           ShopUser su= serverMapper.getUserInfo(user_id);
           content.put("id",su.getId());
           content.put("nickname",su.getNickname());
           content.put("head_image_addr",su.getHeadImageAddr());
           content.put("mobile_phone",su.getMobilePhone());
           content.put("date_of_birth",su.getDateOfBirth());
           content.put("skin_type",su.getSkinType());
           content.put("email",su.getEmail());
           content.put("default_addr",su.getDefaultAddr());
           content.put("shard_count",su.getShardCount());
           content.put("evaluate_count",su.getEvaluateCount());
           content.put("shard_bonus",su.getShardBonus());
           content.put("evaluate_bonus",su.getEvaluateBonus());
           content.put("sex",su.getSex());
           param.put("content",content);
           String jsonString = JSON.toJSONString(param);
           return jsonString;
       }catch (Exception e){
           e.printStackTrace();
           param.put("result","1");
           String jsonString = JSON.toJSONString(param);
           return jsonString;
       }
    }
    //获取用户头像
    @Override
    public String changeUserHead(@Param("user_id")String user_id,@Param("img_data")String img_data ) {
        JSONObject param=new JSONObject();
        try {
            JSONObject content=new JSONObject();
            param.put("result","0");

       }catch (Exception e){

       }
        String jsonString = JSON.toJSONString(param);
        return jsonString;
    }
    //修改用户基本信息
    @Override
    public String changeUserInfo( @Param("user_id")String user_id,@Param("info_type")int info_type,@Param("new_value") String new_value ) {
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        try {
            param.put("result","0");
            if (info_type==0){
                int i= serverMapper.changeNickname(user_id, new_value);
            }else if (info_type==1){
                int i= serverMapper.changeSkinType(Integer.parseInt(user_id), Integer.valueOf(new_value).intValue());
            }else if(info_type==2){
                if(Integer.parseInt(new_value)==0){
                    int i= serverMapper.changeSex(Integer.parseInt(user_id), 1);
                }else{
                    int i= serverMapper.changeSex(Integer.parseInt(user_id), 0);
                }

            }else if(info_type==3){
                int i= serverMapper.changeDate(user_id, new_value);
            }
            param.put("content","");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
    //请求手机验证码
    @Override
    public String sendSmsCode(String user_id ) {
        HashMap param=new HashMap<String,Object>();
        Map content=new HashMap<String,Object>();
        param.put("result","0");
        param.put("content","");
        String jsonString = JSON.toJSONString(param);
        return jsonString;
    }
    //修改手机号
    @Override
    public String changeMobileNumber(String user_id,String sms_code,String new_mobile_number) {
        HashMap param=new HashMap<String,Object>();
        Map content=new HashMap<String,Object>();
        param.put("result","0");
        param.put("content","");
        String jsonString = JSON.toJSONString(param);
        return jsonString;
    }
    //获取订单列表
    @Override
    public String getOrderList (@Param("user_id") String user_id,@Param("page_no")String page_no,@Param("order_state")String order_state){
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject orders=new JSONObject();
        JSONObject items=new JSONObject();
        JSONObject prop1=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        JSONArray jsonArray1=new JSONArray();
        JSONArray jsonArray2=new JSONArray();
        try {
            param.put("result","0");
            //根据userId获取orderId
            List<ShopOrder> shopOrders= serverMapper.getOrderList(user_id);
            for (ShopOrder shopOrder:shopOrders) {
                orders.put("order_id", shopOrder.getId());
                orders.put("page_no", page_no);
                orders.put("order_code", shopOrder.getOrderCode());
                orders.put("order_state", shopOrder.getOrderState());
                orders.put("order_total", shopOrder.getOrderTotal());
                List<Long> ItemsIds = serverMapper.getItemId(shopOrder.getId());
                for (Long ItemsId : ItemsIds) {
                    //获取商品信息
                    ShopItem shopItems = serverMapper.getItemById(ItemsId);
                    ShopOrderItem shopOrderItem = serverMapper.getItemOrder(Long.parseLong(shopOrder.getId().toString()), ItemsId);
                    items.put("item_name", shopItems.getItemName());
                    items.put("item_icon", shopItems.getItemIconAddr());
                  /*  List<ShopOrderItemProperties> props  = serverMapper.getPropsById();
                    for( ShopItemProperties prop:props){
                        prop1.put("item_property_key", prop.getPropertyKey());
                        prop1.put("item_property_value", prop.getPropertyValue());
                        prop1.put("item_count", prop.getCount());
                        prop1.put("item_price", prop.getPrice());
                    }*/
                    String str= JSON.toJSONString(items, SerializerFeature.DisableCircularReferenceDetect);
                    jsonArray.add(JSON.parseObject(str));
                }
                orders.put("items",jsonArray);
                String str= JSON.toJSONString(orders, SerializerFeature.DisableCircularReferenceDetect);
                jsonArray2.add(JSON.parseObject(str));
            }
            param.put("content",jsonArray2);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
    //订单详情
    public String getOrderInfo (@Param("user_id") String user_id,@Param("order_id") String order_id ){
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject items=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            //先去获取订单表信
            param.put("result","0");
            //获取个人订单
           ShopOrder shopOrder= serverMapper.getOrderInfo(user_id, order_id);
            //获取个人信息
            ShopUserAddr shopUserAddr=serverMapper.getUserAddrById(shopOrder.getOrderAddrId().toString());
            //查询订单商品
            List<Long> ItemsIds=serverMapper.getItemId(Long.parseLong(order_id));
            content.put("order_id", shopOrder.getId());
            content.put("order_code", shopOrder.getOrderCode());
            //content.put("item_id",shopOrder.getOrderCode());
            content.put("order_state", shopOrder.getOrderState());
            content.put("order_total", shopOrder.getOrderTotal());
            content.put("freight", shopOrder.getFreight());
            content.put("contact",shopUserAddr.getContact());
            content.put("address",shopUserAddr.getAddress());
            content.put("mobile_phone",shopUserAddr.getMobilePhone());
            content.put("express_code",shopOrder.getExpressCode());
            content.put("create_date",shopOrder.getCreateDate());
            for (Long ItemsId: ItemsIds){
                    //获取商品信息
                    ShopItem shopItems=serverMapper.getItemById(ItemsId);
                    ShopOrderItem shopOrders=serverMapper.getItemOrder(Long.parseLong(order_id),ItemsId);
                    items.put("item_name",shopItems.getItemName());
                    items.put("item_id",ItemsId);
                 /*  items.put("item_icon",shopItems.getItemIconAddr());
                    items.put("item_property_key",shopOrders.getPropertyKey());
                    items.put("item_property_value",shopOrders.getPropertyValue());*/
                    items.put("item_count",shopOrders.getCount());
                    items.put("item_price",shopOrders.getPrice());
                    String str= JSON.toJSONString(items, SerializerFeature.DisableCircularReferenceDetect);
                    jsonArray.add(JSON.parseObject(str));
                }
               content.put("items",jsonArray);
               param.put("content",content);
               String jsonString = JSON.toJSONString(param);
               return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
    //申请退款
    public String requestRefund(@Param("user_id") String user_id,@Param("order_id")String order_id,@Param("item_id")String item_id,@Param("refund_type")String refund_type ,@Param("refund_reason")String refund_reason,@Param("refund_info")String refund_info ,@Param("refund_image_addr")String refund_image_addr){
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject items=new JSONObject();
        //JSONArray jsonArray=new JSONArray();
        try {
            SimpleDateFormat f =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            Date d  =  new Date(f.parse( f.format(date)).getTime());
            ShopUserRefund uf=new ShopUserRefund();
            uf.setUserId(Long.parseLong(user_id));
            uf.setOrderId(Long.parseLong(order_id));
            uf.setCreateDate(d);
            uf.setItemId(Integer.parseInt(item_id));
            uf.setRefundType(Integer.parseInt(refund_type));
            uf.setReason(Integer.parseInt(refund_reason));
            //uf.setRefundCharge(Integer.parseInt(refund_charge));
            uf.setInfo(refund_info);
            uf.setImageAddr(refund_image_addr);
            serverMapper.requestRefund(uf);
            param.put("result","0");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
        }

    //获取退款记录列表
    public String getreFundList( @Param("user_id") String user_id ,@Param("page_no") String page_no){
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            //根据userId获取List
            List <ShopUserRefund> fundLists=serverMapper.getreFundList(user_id);
            param.put("result","0");
            for (ShopUserRefund shopUserRefund:fundLists){
                content.put("order_id",shopUserRefund.getOrderId());
                ShopOrder order=serverMapper.getOrderById(shopUserRefund.getOrderId());
                content.put("order_code",order.getOrderCode());
                //根据商品名称查看商品信息
                 ShopItem item=serverMapper.getItemById(Long.parseLong(shopUserRefund.getItemId().toString()));
                content.put("item_name",SerializeAPI.toUtf8(item.getItemName()));
                content.put("item_icon",item.getItemIconAddr());
                ShopOrderItem orderItem= serverMapper.getItemOrder( Long.parseLong(shopUserRefund.getOrderId().toString()),Long .parseLong(shopUserRefund.getItemId().toString()) );
               /* content.put("item_property_key", orderItem.getPropertyKey());
                content.put("item_property_value",orderItem.getPropertyValue());*/
                content.put("item_count",orderItem.getCount());
                content.put("item_price",item.getPrice());
                content.put("refund_state",shopUserRefund.getRefundState());
                String str= JSON.toJSONString(content, SerializerFeature.DisableCircularReferenceDetect);
                jsonArray.add(JSON.parseObject(str));
            }
            param.put("content",jsonArray);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
    //获取点评记录列表
    public String getUserevaluateList( @Param("user_id") String user_id,@Param("page_no") String page_no ,@Param("list_type")String list_type,@Param("only_bonus")String only_bonus){
        HashMap param=new HashMap<String,Object>();
        Map content=new HashMap<String,Object>();
        param.put("result","0");
        param.put("content","");
        String jsonString = JSON.toJSONString(param);
        return jsonString;
    }
    //获取点评详情
    public String  getEvaluateInfo( String user_id,String evaluate_id){
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            param.put("result","0");
            //根据id查询
            ShopItemEvaluate shopItemEvaluate = serverMapper.getEvaluateInfo(evaluate_id);
            content.put("owner_id",shopItemEvaluate.getUserId());
            content.put("evaluate_id",shopItemEvaluate.getId());
            //查询是否是原创
            content.put("shared_user_id",user_id);
            content.put("item_id",shopItemEvaluate.getItemId());
            //根据itemId差Item
            ShopItem shopItem=serverMapper.getItemById(Long.parseLong(shopItemEvaluate.getItemId().toString()));
            content.put("item _name",shopItem.getItemName());
            content.put("item_icon",shopItem.getItemIconAddr());
            ShopOrderItem orderItem= serverMapper.getItemOrderByItemid(Long .parseLong(shopItemEvaluate.getItemId().toString()));

           /* content.put("item_property_key", orderItem.getPropertyKey());
            content.put("item_property_value",orderItem.getPropertyValue());*/
            content.put("item_advantage",shopItemEvaluate.getAdvantage());
            content.put("item_disadvatage",shopItemEvaluate.getDisadvantage());
            content.put("evaluate_image_addr",shopItemEvaluate.getEvaluateImageAddr());
            content.put("evaluate_vedio_addr",shopItemEvaluate.getEvaluateVedioAddr());
            content.put("grade",shopItemEvaluate.getGrade());
            content.put("create_date",shopItemEvaluate.getCreateDate());
            String str= JSON.toJSONString(content, SerializerFeature.DisableCircularReferenceDetect);
            jsonArray.add(JSON.parseObject(str));
            param.put("content",jsonArray);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }

    //获取地址列表
    public String  getAddressList( String user_id){
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject addr=new JSONObject();

        JSONArray jsonArray=new JSONArray();
        try {
            param.put("result","0");
            ShopUser shopUser=serverMapper.getUserInfo(user_id);
            content.put("default_address_id",shopUser.getDefaultAddr());
            ShopUserAddr sua=new ShopUserAddr();
            sua.setUserId(Long.parseLong(user_id));
            sua.setDeleted(0);
            List<ShopUserAddr> shopUserAddrs=shopUserAddrMapper.selectList(new EntityWrapper<ShopUserAddr>(sua));
            for (ShopUserAddr shopUserAddr :shopUserAddrs){
                addr.put("address_id",shopUserAddr.getId());
                addr.put("contact", SerializeAPI.toUtf8(shopUserAddr.getContact()));
                addr.put("mobile_phone",shopUserAddr.getMobilePhone());
                addr.put("address",SerializeAPI.toUtf8(shopUserAddr.getAddress()));
                String str= JSON.toJSONString(addr, SerializerFeature.DisableCircularReferenceDetect);
                jsonArray.add(JSON.parseObject(str));
            }
            content.put("addrs",jsonArray);
            param.put("content",content);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
    //新增收货地址
    public String  addAddress( @Param("user_id")String user_id,@Param("contact")String contact ,@Param("mobile_phone")String mobile_phone ,@Param("address")String address,String defaults ){
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        //JSONObject addr=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            param.put("result","0");
            //serverMapper.insertAddr(user_id,contact,mobile_phone,address);
            ShopUserAddr addr=new ShopUserAddr();
            addr.setAddress(address);
            addr.setContact(contact);
            addr.setMobilePhone(mobile_phone);
            addr.setUserId(Long.parseLong(user_id));
            addr.setDeleted(0);
            shopUserAddrMapper.insert(addr);
             Integer i=addr.getId();
            System.out.print(i+"=====================");
            //serverMapper.insertAddrO(addr);
            content.put("address_id",i);
            if (Integer.parseInt(defaults)==0){
                ShopUser su=new ShopUser();
                su.setDefaultAddr(i);
                su.setId(Long.parseLong(user_id));
                shopUserMapper.updateById(su);
            }
            String str= JSON.toJSONString(content, SerializerFeature.DisableCircularReferenceDetect);
            jsonArray.add(JSON.parseObject(str));
            param.put("content",jsonArray);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
    //修改收货地址
    public String  editAddress( @Param("user_id")String user_id,@Param("contact")String contact ,@Param("mobile_phone")String mobile_phone ,@Param("address")String address,@Param("address_id")String address_id,String defaults ){
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        //JSONObject addr=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            param.put("result","0");
            ShopUserAddr addr=new ShopUserAddr();
            addr.setId(Integer.parseInt(address_id));
            addr.setAddress(address);
            addr.setContact(contact);
            addr.setMobilePhone(mobile_phone);
            addr.setUserId(Long.parseLong(user_id));
            int i=serverMapper.updateById(addr);
            if (Integer.parseInt(defaults)==0){
                ShopUser su=new ShopUser();
                su.setDefaultAddr(Integer.parseInt(address_id));
                su.setId(Long.parseLong(user_id));
                shopUserMapper.updateById(su);
            }
            content.put("address_id",address_id);
            String str= JSON.toJSONString(content, SerializerFeature.DisableCircularReferenceDetect);
            jsonArray.add(JSON.parseObject(str));
            param.put("content",jsonArray);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
    //删除收货地址
    public String  removeAddress( String user_id, String address_id ){
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();;
        try {
            param.put("result","0");
            ShopUserAddr sua=new ShopUserAddr();
            sua.setId(Integer.parseInt(address_id));
            sua.setDeleted(1);
            sua.setUserId(Long.parseLong(user_id));
            shopUserAddrMapper.updateById(sua);
            //判断是不是默认地址
            ShopUser su=new ShopUser();
            su.setId(Long.parseLong(user_id));
            su.setDefaultAddr(Integer.parseInt(address_id));
            int a =shopUserMapper.selectCount(new EntityWrapper<ShopUser>(su));
            if(a>0) {
                ShopUser su1=new ShopUser();
                String  def=shopUserAddrMapper.selectMaxId(user_id);
                su1.setId(Long.parseLong(user_id));
                su1.setDefaultAddr(Integer.parseInt(def));
                shopUserMapper.updateById(su1);
            }
           // int i=serverMapper.deleteById(address_id);
            param.put("content",null);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
    //获取红包
    public String getGiftCard( String user_id, String page_no ){
        HashMap param=new HashMap<String,Object>();
        Map content=new HashMap<String,Object>();
        param.put("result","0");
        param.put("content","");
        String jsonString = JSON.toJSONString(param);
        return jsonString;
    }
    //获取优惠券
    public String getCoupon( String user_id, String page_no ){
        HashMap param=new HashMap<String,Object>();
        Map content=new HashMap<String,Object>();
        param.put("result","0");
        param.put("content","");
        String jsonString = JSON.toJSONString(param);
        return jsonString;
    }
    //获取佣金及体现信息
    public String getBonus( String user_id, String page_no ){
        HashMap param=new HashMap<String,Object>();
        Map content=new HashMap<String,Object>();
        param.put("result","0");
        param.put("content","");
        String jsonString = JSON.toJSONString(param);
        return jsonString;
    }
    //申请体现
    public String requestBonusPayment( String user_id){
        HashMap param=new HashMap<String,Object>();
        Map content=new HashMap<String,Object>();
        param.put("result","0");
        param.put("content","");
        String jsonString = JSON.toJSONString(param);
        return jsonString;
    }
}
