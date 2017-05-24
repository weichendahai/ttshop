package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.entity.po.PoShopUserRefund;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.service.IShopUserRefundService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户退款记录表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
@Service
public class ShopUserRefundServiceImpl extends BaseServiceImpl<ShopUserRefundMapper, ShopUserRefund> implements IShopUserRefundService {
   @Autowired
   private ShopUserRefundMapper shopUserRefundMapper;
    @Autowired
    private ShopOrderMapper shopOrderMapper;
    @Autowired
    private ShopItemMapper shopItemMapper;
    @Autowired
    private ShopOrderItemPropertiesMapper shopOrderItemPropertiesMapper;
    @Autowired
    private ShopItemPropertiesMapper shopItemPropertiesMapper;
    @Autowired
    private ShopOrderItemMapper shopOrderItemMapper;

    @Autowired
    private ShopUserRefundFeedbackMapper shopUserRefundFeedbackMapper;

    @Override
    public List<PoShopUserRefund> selectPoUserRefundList() {
        return selectPoUserRefundList();
    }

    @Override
    public List<PoShopUserRefund> selectMultiShopUserRefundList(Map<String, Object> map) {
        return baseMapper.selectMultiShopUserRefundList(map);
    }

    @Override
    public Integer selectMultiShopUserRefundCount(Map<String, Object> map) {
        return baseMapper.selectMultiShopUserRefundCount(map);
    }
    //查询详情
    @Override
    public PoShopUserRefund selectShopUserRefundDetailById(Integer refundId) {
        Map<String,Object> map = new HashMap<String, Object>();
        PoShopUserRefund posur = null;
        if(refundId!=null){
            map.put("refundId",refundId);
            List<PoShopUserRefund> refunds = shopUserRefundMapper.selectMultiShopUserRefundList(map);
            if(refunds!=null){
                posur = refunds.get(0);
            }
        }
        map.put("order_id",posur.getOrderId());
        map.remove("refundId");
        List<ShopOrderItemProperties> propertiesList = shopOrderItemPropertiesMapper.selectByMap(map);
        if(propertiesList!=null&&propertiesList.size()>0){
            posur.getShopOrderItem().setCount(propertiesList.get(0).getCount());
        }
        return posur;
    }

    //退款
    @Override
    public String requestRefund(String user_id, String order_id, String item_id, String refund_type, String refund_reason, String refund_info, String refund_image_addr,String order_item_id) {
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
            uf.setRefundState(0);
            if (Integer.parseInt(refund_type)==0){
           //退货
            uf.setRefundCharge(0);
            uf.setOrderItemId(Integer.parseInt(order_item_id));
            }else if (Integer.parseInt(refund_type)==1){
                //退款
                ShopOrderItem soi= shopOrderItemMapper.selectById(order_item_id);
                ShopOrderItemProperties SOPs=new ShopOrderItemProperties();
                SOPs.setOrderItemId(Long.parseLong(order_item_id));
                List<ShopOrderItemProperties> lists=shopOrderItemPropertiesMapper.selectList(new EntityWrapper<ShopOrderItemProperties>(SOPs));
                int count=0;
                for (ShopOrderItemProperties list:lists){
                    count=list.getCount();
                }
                int charge=soi.getPrice()*count;
                uf.setRefundCharge(charge);
            }else {
                //退货退款
                ShopOrderItem soi= shopOrderItemMapper.selectById(order_item_id);
                ShopOrderItemProperties SOPs=new ShopOrderItemProperties();
                SOPs.setOrderItemId(Long.parseLong(order_item_id));
               List<ShopOrderItemProperties> lists=shopOrderItemPropertiesMapper.selectList(new EntityWrapper<ShopOrderItemProperties>(SOPs));
                int count=0;
               for (ShopOrderItemProperties list:lists){
                   count=list.getCount();
               }
                int charge=soi.getPrice()*count;
                uf.setRefundCharge(charge);
                uf.setOrderItemId(Integer.parseInt(order_item_id));
            }
            shopUserRefundMapper.insert(uf);
            //修改这个订单表
            ShopOrderItem soi=new ShopOrderItem();
            soi.setId(Long.parseLong(order_item_id));
            soi.setState(1);
            shopOrderItemMapper.updateById(soi);
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
    @Override
    public String getreFundList(String user_id, String page_no) {
        JSONObject param=new JSONObject();
        JSONObject contents=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject pro =new JSONObject();
        JSONArray jb=new JSONArray();
        try {
            //根据userId获取List
            EntityWrapper<ShopUserRefund> entity=new EntityWrapper<ShopUserRefund>();
            ShopUserRefund sur=new ShopUserRefund();
            sur.setUserId(Long.parseLong(user_id));
            entity.setEntity(sur);
            int i=shopUserRefundMapper.selectCount(entity);
            //获取分页
            int maxPage=i/10;
            if (Integer.parseInt(page_no)>=maxPage){
                contents.put("next_page_no",-1);
                param.put("content",content);
            }else {
                contents.put("next_page_no", Integer.parseInt(page_no) + 1);
            }
                List<ShopUserRefund> fundLists = shopUserRefundMapper.getreFundList(user_id, Integer.parseInt(page_no) * 10);
                param.put("result", "0");
                for (ShopUserRefund shopUserRefund : fundLists) {
                    content.put("order_id", shopUserRefund.getOrderId());
                    ShopOrder order = shopOrderMapper.selectById(shopUserRefund.getOrderId());
                    content.put("order_code", order.getOrderCode());
                    //根据商品名称查看商品信息
                    ShopItem item = shopItemMapper.selectById(shopUserRefund.getItemId());
                    content.put("item_name", SerializeAPI.toUtf8(item.getItemName()));
                    content.put("item_icon", item.getItemIconAddr());
                    //获取退款信息\
                    ShopOrderItemProperties SOips=new ShopOrderItemProperties();
                    SOips.setOrderItemId((long)shopUserRefund.getOrderItemId());
                    List<ShopOrderItemProperties> soips = shopOrderItemPropertiesMapper.selectList(new EntityWrapper<ShopOrderItemProperties>(SOips));
                    int count=0;
                    JSONArray ja=new JSONArray();
                    for (ShopOrderItemProperties soip : soips) {
                        count=soip.getCount();
                        pro.put("item_property_key", soip.getPropertyKey());
                        ShopItemProperties shopItemProperties=new ShopItemProperties();
                        shopItemProperties.setItemId(soip.getItemId());
                        shopItemProperties.setPropertyKey(soip.getPropertyKey());
                        shopItemProperties.setPropertyValue(soip.getPropertyValue());
                        ShopItemProperties sips=shopItemPropertiesMapper.selectOne(shopItemProperties);
                        pro.put("item_property_desc", SerializeAPI.toUtf8(sips.getPropertyDesc()));
                        String str = JSON.toJSONString(pro, SerializerFeature.DisableCircularReferenceDetect);
                        ja.add(JSON.parseObject(str));
                    }
                    ShopOrderItem soim=new ShopOrderItem();
                    soim.setItemId(shopUserRefund.getItemId());
                    soim.setOrderId((long)shopUserRefund.getOrderId());
                    ShopOrderItem soIm = shopOrderItemMapper.selectOne(soim);
                    content.put("item_count", count);
                    content.put("item_price", soIm.getPrice());
                    content.put("propertys", ja);
                    content.put("refund_state", shopUserRefund.getRefundState());
                    content.put("refund_type",shopUserRefund.getRefundType());
                    String str = JSON.toJSONString(content, SerializerFeature.DisableCircularReferenceDetect);
                    jb.add(JSON.parseObject(str));
                }
                contents.put("refunds", jb);
                param.put("content",contents);
                String jsonString = JSON.toJSONString(param);
                return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
}
