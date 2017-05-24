package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.service.IShopOrderItemPropertiesService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单商品关系表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-31
 */
@Service
public class ShopOrderItemPropertiesServiceImpl extends BaseServiceImpl<ShopOrderItemPropertiesMapper, ShopOrderItemProperties> implements IShopOrderItemPropertiesService {
    @Autowired
    private ShopOrderMapper shopOrderMapper;
    @Autowired
    private ShopUserAddrMapper shopUserAddrMapper;
    @Autowired
    private ShopGiftCardMapper shopGiftCardMapper;
    @Autowired
    private ShopCouponConditionMapper shopCouponConditionMapper;
    @Autowired
    private ShopOrderItemMapper shopOrderItemMapper;
    @Autowired
    private ShopItemMapper shopItemMapper;
    @Autowired
    private ShopOrderItemPropertiesMapper shopOrderItemPropertiesMapper;
    @Autowired
    private ShopItemPropertiesMapper shopItemPropertiesMapper;
    @Override
    public List<ShopOrderItemProperties> selectShopOrderItemByOrderCode(Long orderCode) {
        return baseMapper.selectShopOrderItemByOrderCode(orderCode);
    }

    @Override
    public List<ShopOrderItemProperties> selectMultiShopOrderItemPropertiesList(Map<String, Object> map) {
        return baseMapper.selectMultiShopOrderItemPropertiesList(map);
    }

    @Override
    public Integer selectMultiShopOrderItemPropertiesCount(Map<String, Object> map) {
        return baseMapper.selectMultiShopOrderItemPropertiesCount(map);
    }

    @Override
    public List<ShopOrderItemProperties> selectMultiShopOrderItemProperties(Map<String, Object> map) {
        return baseMapper.selectMultiShopOrderItemProperties(map);
    }

    @Override
    public String getOrderList(String user_id, String page_no, String order_state) {
        JSONObject param =new JSONObject();
        JSONObject content= new JSONObject();
        try{
            param.put("result","0");
            //获取用户所有的订单
            param.put("content","");
            return null;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            return JSON.toJSONString(param);
        }
        //return ;
    }
//获取订单明细
    @Override
    public String getOrderInfo(String user_id, String order_id) {
        JSONObject param =new JSONObject();
        JSONObject content= new JSONObject();
        JSONObject item=new JSONObject();
        JSONObject itemProper=new JSONObject();
        JSONArray jb=new JSONArray();
        try{
            param.put("result","0");
            //根据订单id获取订单信息
            ShopOrder so=shopOrderMapper.selectById(order_id);
            content.put("order_id",so.getId());
            content.put("order_code",so.getOrderCode());
            content.put("order_state",so.getOrderState());
            content.put("order_total",so.getOrderTotal());
            content.put("freight",so.getFreight());
            //根据addrId获取用户信息
            ShopUserAddr sua=shopUserAddrMapper.selectById(so.getOrderAddrId());
            content.put("contact", SerializeAPI.toUtf8(sua.getContact()));
            content.put("address",SerializeAPI.toUtf8(sua.getAddress()));
            content.put("mobile_phone",sua.getMobilePhone());
            //获取红包的名字
            if(so.getUserGiftCardId()==null||so.getUserGiftCardId()<0){
            }else {
           ShopGiftCard sgc= shopGiftCardMapper.selectById(so.getUserGiftCardId());
            content.put("gift_card_desc",SerializeAPI.toUtf8(sgc.getName()));
            }
            //获取优惠券描述
            if(so.getUserCouponId()==null||so.getUserCouponId()<0){
            }else {
         ShopCouponCondition scc=  shopCouponConditionMapper.selectById(so.getUserCouponId());
            content.put("coupon_desc",SerializeAPI.toUtf8(scc.getConditionDesc()));
            }
            content.put("express_code",so.getExpressCode());
            content.put("create_date", DateUtils.Date2String(so.getCreateDate()));
            //根据订单列表拿出所有的商品
            ShopOrderItem soitem=new ShopOrderItem();
            soitem.setOrderId(Long.parseLong(order_id));
            List<ShopOrderItem> lists=shopOrderItemMapper.selectList(new EntityWrapper<ShopOrderItem>(soitem));
            for (ShopOrderItem list:lists){
                ShopItem si=shopItemMapper.selectById(list.getItemId());
                item.put("item_id",si.getId());
                item.put("item_name",SerializeAPI.toUtf8(si.getItemName()));
                item.put("item_icon",si.getItemIconAddr());
                item.put("item_state",list.getState());
                item.put("order_item_id",list.getId());
                ShopOrderItemProperties soip=new ShopOrderItemProperties();
                soip.setOrderId(Long.parseLong(order_id));
                soip.setItemId(si.getId());
                if ("null".equals(String.valueOf(list.getCount()))){
                JSONArray ja=new JSONArray();
                List<ShopOrderItemProperties> soips = shopOrderItemPropertiesMapper.seletctByItemId(so.getId(), list.getItemId(), list.getId());
                int i=0;
                for (ShopOrderItemProperties soItems:soips){
                    itemProper.put("item_property_key",soItems.getPropertyKey());
                    itemProper.put("item_property_value",soItems.getPropertyValue());
                    ShopItemProperties Sip=new ShopItemProperties();
                    i=soItems.getCount();
                    Sip.setItemId(soItems.getItemId());
                    Sip.setPropertyKey(soItems.getPropertyKey());
                    Sip.setPropertyValue(soItems.getPropertyValue());
                    ShopItemProperties SHopPro=shopItemPropertiesMapper.selectOne(Sip);
                    itemProper.put("item_property_desc",SerializeAPI.toUtf8(SHopPro.getPropertyDesc()));
                    String str = JSON.toJSONString(itemProper, SerializerFeature.DisableCircularReferenceDetect);
                    //jsonArray.add(JSON.parseObject(str));
                    ja.add(JSON.parseObject(str));
                }
                item.put("item_propertys", ja);

                item.put("item_count", i);
                }else {
                    item.put("item_count", list.getCount());
                    item.put("item_propertys", "");
                }
                item.put("item_price",list.getPrice());
                String str = JSON.toJSONString(item, SerializerFeature.DisableCircularReferenceDetect);
                //jsonArray.add(JSON.parseObject(str));
                jb.add(JSON.parseObject(str));
            }
            content.put("items",jb);
            param.put("content",content);
            return JSON.toJSONString(param);
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            return JSON.toJSONString(param);
        }
    }

}
