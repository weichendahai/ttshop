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
import com.baomidou.springwind.service.IShopOrderService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopOrderServiceImpl extends BaseServiceImpl<ShopOrderMapper, ShopOrder> implements IShopOrderService {
  @Autowired
  private ShopOrderMapper shopOrderMapper;
    @Autowired
    private ShopOrderItemPropertiesMapper shopOrderItemPropertiesMapper;
    @Autowired
    private ShopOrderItemMapper shopOrderItemMapper;
    @Autowired
    private ShopItemMapper shopItemMapper;
    @Autowired
    private ShopItemPropertiesMapper shopItemPropertiesMapper;
    @Autowired
    private ShopUserAddrMapper shopUserAddrMapper;
    @Override
    public Integer selectMultiShopOrderListTotal(Map<String,Object> map) {
        return baseMapper.selectMultiShopOrderListTotal(map);
    }

    @Override
    public List<ShopOrder> selectMultiShopOrderList(Integer _index, Integer _size,String sortField) {
         return baseMapper.selectMultiShopOrderList(_index,_size,sortField);
    }

    @Override
    public List<ShopOrder> selectMultiShopOrderListByMap(Map<String, Object> map) {

        return baseMapper.selectMultiShopOrderListByMap(map);
    }

    @Override
    public Integer updateOrderState(ShopOrder shopOrder) {
        shopOrder.setModifyDate(new Date());
        Integer integer = baseMapper.updateById(shopOrder);
        return integer;
    }
/*取消订单装态*/
    @Override
    public String cancelOrder(String order_id, String user_id) {
        JSONObject param=new JSONObject();
        try {
        //根据订单id获取对应的值
          param.put("result","0");
            ShopOrder So=new ShopOrder();
            So.setId(Long.parseLong(order_id));
            So.setOrderState(4);
         shopOrderMapper.updateById(So);
        }catch (Exception e){
            param.put("result","1");
            e.printStackTrace();
        }
       return JSON.toJSONString(param);
    }
/*导出订单exce*/
    @Override
    public List<ShopExportOrder> exportShopOrderListByMap(Map<String, Object> map) {
        List<ShopExportOrder> lists=new ArrayList<ShopExportOrder>();
        try {
            List<ShopOrder> shopOrders=baseMapper.selectMultiShopOrderListByMap1(map);
            for (ShopOrder shopOrder:shopOrders){
                //根据商品划分
                ShopExportOrder shopExportOrder=new ShopExportOrder();
                ShopOrderItem soi=new ShopOrderItem();
                soi.setOrderId(shopOrder.getId());
               List<ShopOrderItem> items=shopOrderItemMapper.selectList(new EntityWrapper<ShopOrderItem>(soi));
                for (ShopOrderItem item:items){
                    //判断商品是否有属性
                    String str="";
                    /*    0  代付款    1  代发货   2  待收货   3带评价 4已完成 */
                    if (shopOrder.getOrderState()==0){
                      str="待付款       ";
                    }else if(shopOrder.getOrderState()==1){
                      str="待发货       ";
                    }else if (shopOrder.getOrderState()==2){
                       str="待收货      ";
                    }else{
                        str="已完成      ";
                    }
                    shopExportOrder.setOrderState(str);
                    shopExportOrder.setOrderCode(shopOrder.getOrderCode());
                    if (null==shopOrder.getExpressCode() || shopOrder.getExpressCode().length()<=0){
                        shopExportOrder.setExpressCode("     　           ");
                    }else {
                        shopExportOrder.setExpressCode(shopOrder.getExpressCode());
                    }
                    shopExportOrder.setCreateDate(DateUtils.Date2String2(shopOrder.getCreateDate()));
                    if (null==shopOrder.getModifyDate()){
                        shopExportOrder.setModifyDate("             　      ");
                    }else {
                    shopExportOrder.setModifyDate(DateUtils.Date2String2(shopOrder.getModifyDate()));
                    }
                    shopExportOrder.setFreight(shopOrder.getFreight()+"    ");
                    shopExportOrder.setOrderTotal(shopOrder.getOrderTotal()+"            ");
                    shopExportOrder.setTransNumber(shopOrder.getTransNumber());
                    shopExportOrder.setUserId(shopOrder.getUserId());
                    if (item.getCount()!=null){
                        shopExportOrder.setPropertyDesc("");
                        shopExportOrder.setItemCount(item.getCount().toString());
                    }else {
                        ShopOrderItemProperties shopOrderItemProperties=new ShopOrderItemProperties();
                        shopOrderItemProperties.setOrderItemId(item.getId());
                        List<ShopOrderItemProperties> propertieses=shopOrderItemPropertiesMapper.selectList(new EntityWrapper<ShopOrderItemProperties>(shopOrderItemProperties));
                        StringBuffer sb= new StringBuffer();
                        int count=0;
                        for (ShopOrderItemProperties propertiese:propertieses){
                            ShopItemProperties  SIPs=new ShopItemProperties();
                            SIPs.setPropertyKey(propertiese.getPropertyKey());
                            SIPs.setPropertyValue(propertiese.getPropertyValue());
                            SIPs.setItemId(item.getItemId());
                           ShopItemProperties SIPS= shopItemPropertiesMapper.selectOne(SIPs);
                            sb.append(SIPS.getPropertyDesc()+",");
                            count=propertiese.getCount();
                        }
                        shopExportOrder.setPropertyDesc(sb.substring(0, sb.length() - 1).toString());
                        shopExportOrder.setItemCount(String.valueOf(count)+"         ");
                    }
                    ShopItem SI= shopItemMapper.selectById(item.getItemId());
                    shopExportOrder.setItemName(SI.getItemName());
                    //根据订单用户拿到姓名
                   ShopUserAddr Sua= shopUserAddrMapper.selectById(shopOrder.getOrderAddrId());
                shopExportOrder.setContact(Sua.getContact());
                shopExportOrder.setAddress(Sua.getAddress());
                shopExportOrder.setTelephone(Sua.getMobilePhone());
                }
                lists.add(shopExportOrder);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return lists;
    }

    //获取用户所有的订单列表
    @Override
    public String getOrderList(String user_id, String page_no, String order_state) {
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject orders=new JSONObject();
        JSONObject items=new JSONObject();
        JSONArray jc=new JSONArray();
        JSONObject pro=new JSONObject();
        try{
            //获取分页的中页数
            param.put("result","0");
            EntityWrapper<ShopOrder> entity=new EntityWrapper<ShopOrder>();
            ShopOrder sor=new ShopOrder();
            sor.setUserId(Long.parseLong(user_id));
            sor.setOrderState(Integer.parseInt(order_state));
            entity.setEntity(sor);
            int i=shopOrderMapper.selectCount(entity);
            //计算最大的页码数
            int maxPage=i/10;
            if (Integer.parseInt(page_no)>=maxPage){
                content.put("next_page_no",-1);
            }else {
                content.put("next_page_no", Integer.parseInt(page_no) + 1);
            }
                List<ShopOrder> sos = shopOrderMapper.selectOrderByUserId(user_id, Integer.parseInt(order_state), Integer.parseInt(page_no) * 10);
                for (ShopOrder so : sos) {
                    orders.put("order_id", so.getId());
                    orders.put("order_code", so.getOrderCode());
                    orders.put("order_state", so.getOrderState());
                    orders.put("order_total", so.getOrderTotal());
                    //根据订单id和商品id获取对应的shopProperties
                    List<ShopOrderItem> sois = shopOrderItemMapper.selectOrderItemListByOrderId(so.getId());
                    JSONArray jb=new JSONArray();
                    for (ShopOrderItem soi : sois) {
                        //获取订单对应的itemId和orderid
                        ShopItem si = shopItemMapper.selectById(soi.getItemId());
                        //检测这个产品是否存在重复属性
                        items.put("item_id", si.getId());
                        items.put("item_name", SerializeAPI.toUtf8(si.getItemName()) );
                        items.put("item_icon", si.getItemIconAddr());
                        JSONArray ja=new JSONArray();
                        List<ShopOrderItemProperties> sops = shopOrderItemPropertiesMapper.seletctByItemId(so.getId(), soi.getItemId(),soi.getId());
                        int count=0;
                        if(null==sops || sops.size()<=0){
                            items.put("item_propertys", "");
                            items.put("item_count", soi.getCount());
                            items.put("item_price", soi.getPrice());
                        }else {
                            for (ShopOrderItemProperties sop : sops) {
                                // List<ShopOrderItemProperties> soips = shopOrderItemPropertiesMapper.getItemOrder(new Long(so.getId()).intValue(), soi.getItemId());
                                pro.put("item_property_key", sop.getPropertyKey());
                                ShopItemProperties shopItemProperties = new ShopItemProperties();
                                shopItemProperties.setItemId(sop.getItemId());
                                shopItemProperties.setPropertyKey(sop.getPropertyKey());
                                shopItemProperties.setPropertyValue(sop.getPropertyValue());
                                ShopItemProperties sips = shopItemPropertiesMapper.selectOne(shopItemProperties);
                                pro.put("item_property_desc", SerializeAPI.toUtf8(sips.getPropertyDesc()));
                                String str = JSON.toJSONString(pro, SerializerFeature.DisableCircularReferenceDetect);
                                ja.add(JSON.parseObject(str));
                                count = sop.getCount();
                            }
                            items.put("item_propertys", ja);

                        items.put("item_count", count);
                        items.put("item_price", soi.getPrice());
                        }
                        String str = JSON.toJSONString(items, SerializerFeature.DisableCircularReferenceDetect);
                        //jsonArray.add(JSON.parseObject(str));
                        jb.add(JSON.parseObject(str));
                    }
                    orders.put("items", jb);
                    String str = JSON.toJSONString(orders, SerializerFeature.DisableCircularReferenceDetect);
                    jc.add(JSON.parseObject(str));
                }
                content.put("orders", jc);
                param.put("content", content);
            return JSON.toJSONString(param);
        } catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            param.put("content",SerializeAPI.toUtf8("订单查询异常"));
            return JSON.toJSONString(param);
        }
    }

}
