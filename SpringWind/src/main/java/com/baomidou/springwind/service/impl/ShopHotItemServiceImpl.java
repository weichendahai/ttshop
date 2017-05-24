package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.redis.MapUtil;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.redis.redisUtil;
import com.baomidou.springwind.service.IShopHotItemService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 爆款商品，大多数都是商品的冗余字段，应该存放在Redis中 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
@Service
public class ShopHotItemServiceImpl extends BaseServiceImpl<ShopHotItemMapper, ShopHotItem> implements IShopHotItemService {

    @Autowired
    private ShopHotItemMapper shopHotItemMapper;

    @Autowired
    private ShopOptionMapper shopOptionMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;
    @Autowired
    private ShopItemPropertiesMapper shopItemPropertiesMapper;
    @Autowired
    private ShopItemPriceMapper    shopItemPriceMapper;
    @Autowired
    private ShopItemMapper shopItemMapper;
    @Autowired
    private ShopItemPropertyNameMapper shopItemPropertyNameMapper;
    @Override
    public Integer getMaxSessionNo() {
       int sessionNo =  shopHotItemMapper.getMaxSessionNo();
        return sessionNo;
    }

    //更新爆款商品列表
    @Override
    public List<ShopHotItem> selectUpdateShopHotItemList( ) {
        Integer sessionNo = shopHotItemMapper.getMaxSessionNo();
        if(sessionNo==null){
            sessionNo=0;
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("option_key",6);
        List<ShopOption> options = shopOptionMapper.selectByMap(map);
        Date maxCreateDate = baseMapper.selectMaxCreateDate(sessionNo);
        if(maxCreateDate!=null){
            map.put("startDate", maxCreateDate);
        }else {
            try {
                maxCreateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-01 12:00:00");
                map.put("startDate", maxCreateDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Date d =  new Date();
        map.remove("option_key");
        map.put("endDate",d);
        map.put("limitCount",Integer.parseInt(options.get(0).getOptionValue()));
        List<ShopHotItem> hotItems = baseMapper.selectStatisticShopHotItemList(map);
            sessionNo++;
            if(hotItems!=null&&hotItems.size()>0){
                int i = 0;
                for(ShopHotItem shi:hotItems){
                    shi.setSessionNo(sessionNo);
                    shi.setSortFactor(i++);
                    shi.setCreateDate(d);
                }
            }
        return hotItems;
    }

    /**
     * 添加爆款列表到redis
     * @param list
     */
    @Override
    public void addHotItemListToRedis(List<ShopHotItem> list) {
        for(ShopHotItem shi:list){
            this.getInfoFromServer(shi);
        }
    }

    @Override
    public boolean delShopHotItemByIdAndSessionNo(Integer id, Integer sessionNo) {
        return shopHotItemMapper.delShopHotItemByIdAndSessionNo(id,sessionNo);
    }

    @Override
    public List<ShopHotItem> selectShopHotItemLimit(Integer _index, Integer _size) {
       List<ShopHotItem> list  =  shopHotItemMapper.selectShopHotItemLimit(_index, _size);

        return list;
    }

    @Override
    public List<ShopHotItem> selectHotItemSessionNoAndDate() {
        return shopHotItemMapper.selectHotItemSessionNoAndDate();
    }
//获取爆款列表
    @Override
    public String getHotitemList(Long page_no, String season_no) {
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject hot_items=new JSONObject();
        JSONObject propertys=new JSONObject();
        JSONObject values=new JSONObject();
        JSONArray jc=new JSONArray();
        String str2="hotItemList_#";
        String str="hotItemList_#"+season_no;
        Set<String> keys=null;
        try {
            param.put("result","0");
            Jedis client= redisUtil.getJedis();
            //String hotitemlist= client.get(str);
            //redis分页
            if (Integer.parseInt(season_no)<0){
                String str1=shopHotItemMapper.selectMaxSeaNo();
                keys=client.keys(str2+str1+"*");
                season_no=str1;
                content.put("season_no",str1);
            }else {
                keys=client.keys(str+"*");
                content.put("season_no",season_no);
            }
            Long count = client.hlen(str+"*");
            Long  maxPage=count/10;
            if (page_no+1>maxPage){
                content.put("next_page_no",-1);
                param.put("content",content);
            }else {
                content.put("next_page_no",page_no+1);
            }

            //redis分页
            Object[] newArray= MapUtil.getPage(page_no.intValue(),keys);
            List<String> page=new ArrayList<String>();
            for (int i=0;i<newArray.length;i++){
                if (newArray[i]!=null){
                  page.add(newArray[i].toString());
                }
            }
            //获取所有的爆款列表
            for (String key:page) {
                JSONArray jb = new JSONArray();
                String hotString = client.get(key);
                ShopHotItem shopBanner = JSON.parseObject(hotString, ShopHotItem.class);
                hot_items.put("item_id", shopBanner.getItemId());
                ShopItem si = shopItemMapper.selectById(shopBanner.getItemId());
                if (si.getItemState()==0){
                    hot_items.put("item_state",1);
                }else {
                    hot_items.put("item_state","");
                }

                hot_items.put("item_price_discount", DateUtils.getDiscount(si.getPrice(), si.getDiscountRate(), si.getDiscountValue()));
                List<String> shopItemPropertiesList = shopItemPropertiesMapper.selecetListByItemId(shopBanner.getItemId().toString());
                if (shopItemPropertiesList == null) {
                    hot_items.put("propertys", "");
                } else {
                for (String key1 : shopItemPropertiesList) {
                    List<ShopItemProperties> shopItemProperties = null;
                    JSONArray ja = new JSONArray();
                    propertys.put("property_key", key1);
                    ShopItemPropertyName SIPN1=new ShopItemPropertyName();
                    SIPN1.setPropertyKey(Integer.parseInt(key1));
                    ShopItemPropertyName SIPN=  shopItemPropertyNameMapper.selectOne(SIPN1);
                    propertys.put("property_key_desc", SerializeAPI.toUtf8(SIPN.getPropertyName()));
                    shopItemProperties = shopItemPropertiesMapper.selecetValueByItemId(shopBanner.getItemId().toString(), key1);
                    if (shopItemProperties == null) {

                    } else {
                        for (ShopItemProperties SIP : shopItemProperties) {
                            values.put("property_value", SIP.getPropertyValue());
                            values.put("property_desc", URLEncoder.encode(SIP.getPropertyDesc(), "utf-8"));
                            String str12 = JSON.toJSONString(values, SerializerFeature.DisableCircularReferenceDetect);
                            ja.add(JSON.parseObject(str12));
                        }
                    }
                    propertys.put("values", ja);
                    String str11 = JSON.toJSONString(propertys, SerializerFeature.DisableCircularReferenceDetect);
                    jb.add(JSON.parseObject(str11));
                }
                //获取描述信息
                hot_items.put("propertys", jb);
                //获取商品对应价格
                ShopItemPrice shopItemPrice = new ShopItemPrice();
                shopItemPrice.setItemId(shopBanner.getItemId());
                List<ShopItemPrice> lists = shopItemPriceMapper.selectList(new EntityWrapper<ShopItemPrice>(shopItemPrice));
                JSONArray property_prices_key = new JSONArray();
                JSONArray property_prices_value = new JSONArray();
                for (ShopItemPrice list : lists) {
                    //   property_prices.put(list.getPropertyPath(),list.getPrice());
                    property_prices_key.add(list.getPropertyPath());
                    property_prices_value.add(list.getPrice());
                }
                hot_items.put("property_prices_key", property_prices_key);
                hot_items.put("property_prices_value", property_prices_value);
            }
                        hot_items.put("item_name", URLEncoder.encode(shopBanner.getItemName(),"utf-8") );
                        hot_items.put("item_icon_addr",shopBanner.getItemIconAddr());
                        hot_items.put("evaluate_count", shopBanner.getEvaluateCount());
                        hot_items.put("session_no",shopBanner.getSessionNo());
                        hot_items.put("grade",shopBanner.getGrade());
                        hot_items.put("price",shopBanner.getPrice());
                        hot_items.put("advantage",URLEncoder.encode(shopBanner.getAdvantage(),"utf-8") );
                        jc.add(JSON.parseObject(JSON.toJSONString(hot_items, SerializerFeature.DisableCircularReferenceDetect)));
            }
           // List<ShopHotItem> hotItems = JSON.parseArray(hotitemlist, ShopHotItem.class);
          /*  ShopBanner shopBanner=JSON.parseObject(bannerStr, ShopBanner.class);
            for (ShopHotItem hotItem:hotItems){
                hot_items.put("item_id",hotItem.getItemId());
                hot_items.put("item_name",hotItem.getItemName());
                hot_items.put("item_icon_addr",hotItem.getItemIconAddr());
                hot_items.put("evaluate_count",hotItem.getEvaluateCount());
                hot_items.put("grade",hotItem.getGrade());
                hot_items.put("price",hotItem.getPrice());
                hot_items.put("advantage",hotItem.getAdvantage());
                ja.add(JSON.parseObject(JSON.toJSONString(hot_items, SerializerFeature.DisableCircularReferenceDetect)));
            }*/
            redisUtil.returnResource(client);
            content.put("hot_items",jc);
            param.put("content",content);
            return JSON.toJSONString(param);
        }catch (Exception e) {
            e.printStackTrace();
            param.put("result", "1");
            param.put("content", "爆款列表没有");
            return JSON.toJSONString(param);
        }
    }
   //获取后台爆款列表
    @Override
    public void getInfoFromServer(ShopHotItem shopHotItem) {
        JSONObject hot=new JSONObject();
     try {
        Jedis client= redisUtil.getJedis();
             String str="hotItemList_#"+shopHotItem.getSessionNo()+"_#"+shopHotItem.getId();
             hot.put("season_no", shopHotItem.getSessionNo().toString());
             hot.put("item_id", shopHotItem.getItemId().toString());
             hot.put("item_name",shopHotItem.getItemName());
             hot.put("item_icon_addr",shopHotItem.getItemIconAddr());
             hot.put("evaluate_count",shopHotItem.getEvaluateCount().toString());
             hot.put("grade",shopHotItem.getGrade().toString());
             hot.put("price",shopHotItem.getPrice().toString());
             hot.put("advantage",shopHotItem.getAdvantage());
             String key=shopHotItem.getId()+"#"+shopHotItem.getSessionNo();
             String obj= JSON.toJSONString(hot, SerializerFeature.DisableCircularReferenceDetect);
         client.set(str, obj);
         redisUtil.returnResource(client);
     }catch (Exception e){
         e.printStackTrace();
     }
    }
    //删除爆款数据
    @Override
    public void delInfoFromServer(ShopHotItem shopHotItem) {
        String str="hotItemList_#"+shopHotItem.getSessionNo()+"#"+shopHotItem.getId();
        try{
            String key=shopHotItem.getId().toString();
            Jedis client= redisUtil.getJedis();
            client.hdel(str,key);
            redisUtil.returnResource(client);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //修改爆款数据
    @Override
    public void dInfoFromServer(ShopHotItem shopHotItem) {
        String str="hotItemList_#"+shopHotItem.getSessionNo()+"#"+shopHotItem.getId();
        try{
            String key=shopHotItem.getId().toString();
            Jedis client= redisUtil.getJedis();
            client.hdel(str,key);
            HashMap<String, String> itemMap = new HashMap<String, String>();
            itemMap.put(key ,JSON.toJSONString(shopHotItem));
            client.hmset(str,itemMap);
            redisUtil.returnResource(client);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    * 测试main方法
    * */

    public static void main(String args[]) {
        List<ShopHotItem> lists=new LinkedList<ShopHotItem>();
        ShopHotItem sh=new ShopHotItem();
        ShopHotItem sh1=new ShopHotItem();
        sh.setId(1);
        sh.setItemId(1);
        sh.setItemName("口红");
        sh.setItemIconAddr("http://img.ciaotalking.com/shopimg/20170412/2bf061ce-be85-45ad-9a4d-21266599b1f8.jpeg");
        sh.setEvaluateCount(100);
        sh.setGrade(86);
        sh.setPrice(99);
        sh.setAdvantage("好使啊");
        sh.setSessionNo(1);
        sh.setAmount(1200);
        sh.setCreateDate(new Date());
        //
        sh1.setId(5);
        sh1.setItemId(2);
        sh1.setItemName("眼霜");
        sh1.setItemIconAddr("http://img.ciaotalking.com/shopimg/20170412/2bf061ce-be85-45ad-9a4d-21266599b1f8.jpeg");
        sh1.setEvaluateCount(888);
        sh1.setGrade(90);
        sh1.setPrice(99);
        sh1.setAdvantage("好用");
        sh1.setSessionNo(2);
        sh1.setAmount(1200);
        sh1.setCreateDate(new Date());
        lists.add(sh);
        lists.add(sh1);
 /*      getInfoFromServer1(sh);
        getInfoFromServer1(sh1);
        // System.out.println("Hello World!");*/
    }
    //测试
    public static  void getInfoFromServer1(ShopHotItem shopHotItem) {
        JSONObject hot=new JSONObject();
        try {
            Jedis client= redisUtil.getJedis();
            String str="hotItemList_#"+shopHotItem.getSessionNo()+"_#"+shopHotItem.getId();
            hot.put("season_no", shopHotItem.getSessionNo().toString());
            hot.put("item_id", shopHotItem.getItemId().toString());
            hot.put("item_name", shopHotItem.getItemName());
            hot.put("item_icon_addr", shopHotItem.getItemIconAddr());
            hot.put("evaluate_count",shopHotItem.getEvaluateCount().toString());
            hot.put("grade",shopHotItem.getGrade().toString());
            hot.put("price",shopHotItem.getPrice().toString());
            hot.put("advantage",shopHotItem.getAdvantage());
            String key=shopHotItem.getId()+"#"+shopHotItem.getSessionNo();
            String obj= JSON.toJSONString(hot, SerializerFeature.DisableCircularReferenceDetect);
            client.set(str, obj);
            redisUtil.returnResource(client);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
