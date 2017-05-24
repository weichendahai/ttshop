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
import com.baomidou.springwind.redis.redisUtil;
import com.baomidou.springwind.service.IShopSearchHistoryService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/*import java.sql.Wrapper;*/
/**/

/**
 * <p>
 * 历史搜索记录表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopSearchHistoryServiceImpl extends BaseServiceImpl<ShopSearchHistoryMapper, ShopSearchHistory> implements IShopSearchHistoryService {
    @Autowired
    private ShopSearchHistoryMapper shopSearchHistoryMapper;
    @Autowired
    private ShopItemMapper shopItemMapper;
    @Autowired
    private ShopItemPropertiesMapper shopItemPropertiesMapper;
    @Autowired
    private ShopOrderItemPropertiesMapper shopOrderItemPropertiesMapper;
    @Autowired
    private ShopHotSearchKeywordMapper shopHotSearchKeywordMapper;
    @Autowired
    private ShopItemCategoryMapper shopItemCategoryMapper;
    @Autowired
   private ShopItemPriceMapper shopItemPriceMapper;
    @Autowired
    private ShopOrderItemMapper shopOrderItemMapper;
    @Autowired
    private ShopItemPropertyNameMapper shopItemPropertyNameMapper;
    @Override
    public String searchPrepare(String user_id) {
        JSONObject param=new JSONObject();
        JSONObject content =new JSONObject();
        JSONArray ja=new JSONArray();
        JSONArray jb=new JSONArray();
        try {
            int limit=10;
            param.put("result","0");
            //获取热搜记录
            List<String> hots=shopHotSearchKeywordMapper.selectByKeyword();
           // List<String> hots=shopSearchHistoryMapper.getHotSearch(limit);
            for (String hot:hots){
                jb.add(SerializeAPI.toUtf8(hot));
            }
            content.put("hot_keywords",jb);
            //获取搜索记录
            List<String> lists=shopSearchHistoryMapper.getHistoryByUserId(user_id, limit);
            for (String list:lists){
                if (list!=null&&list.length()>0){
                    ja.add(SerializeAPI.toUtf8(list));
                }
               // String str= JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);

            }
            content.put("history_keywords",ja);
            param.put("content", content);
            return JSON.toJSONString(param);
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            param.put("content",SerializeAPI.toUtf8("没有搜索记录"));
            return JSON.toJSONString(param);
        }
    }
    //模糊搜索
    @Override
    public String search(String user_id, String keyword,String page_no,String category_id,String order_price,String order_count) {
        //根据关键词匹配搜索资源
        JSONObject param =new JSONObject();
        JSONObject content =new JSONObject();
        JSONObject items=new JSONObject();
        JSONObject item_propertys=new JSONObject();
        JSONObject values=new JSONObject();
        JSONObject categorys=new JSONObject();
        JSONObject subs=new JSONObject();
        JSONArray jc=new JSONArray();
        JSONArray je=new JSONArray();
        //判断这个id是几级的id
        try{
            //String str=getKeyWord(keyword);
            String seaech1=("%"+keyword+"%");
            param.put("result","0");
            List<ShopItem> shopItems=null;
           if (Integer.parseInt(category_id)==0){
            //获取所有匹配字段获取所有的数量
            int i= shopItemMapper.selectAll(seaech1);
            int maxPage=i/10;
            if (Integer.parseInt(page_no)+1>maxPage){
               content.put("next_page_no",-1);
             //  param.put("content",content);
            }else {
                content.put("next_page_no",Integer.parseInt(page_no)+1);
            }
             shopItems= shopItemMapper.selectByKeyword(seaech1,Integer.parseInt(page_no)*10,Integer.parseInt(order_price),Integer.parseInt(order_count));
           }else {
               //判断他是几级菜单
               Jedis client=redisUtil.getJedis();
               String  str="secondary_#"+"2_#"+category_id;
              // List<String> secValues=null;
               int i=0;
               if (client.exists(str)){
                   shopItems= shopItemMapper.selectByKeyword1(seaech1,category_id, Integer.parseInt(page_no)*10,Integer.parseInt(order_price),Integer.parseInt(order_count));
                    i= shopItemMapper.selectAll1(seaech1,category_id);
               }else {
                   shopItems = shopItemMapper.selectByKeyword2(seaech1,category_id,Integer.parseInt(page_no)*10,Integer.parseInt(order_price),Integer.parseInt(order_count));
                   i= shopItemMapper.selectAll2(seaech1,category_id);
               }
               //int i= shopItemMapper.selectAllCategory(secValues);
               int maxPage=i/10;
               if (Integer.parseInt(page_no)+1>maxPage){
                   content.put("next_page_no",-1);
                 //  param.put("content",content);
               }else {
                   content.put("next_page_no",Integer.parseInt(page_no)+1);
               }
               // shopItems= shopItemMapper.selectByCategory(secValues, Integer.parseInt(page_no) * 10);
           }
            for (ShopItem si:shopItems) {
                items.put("item_id", si.getId());
                items.put("item_name",SerializeAPI.toUtf8(si.getItemName()));
                items.put("item_icon", si.getItemIconAddr());
              /*  EntityWrapper<ShopItemProperties> entity = new EntityWrapper<ShopItemProperties>();*/
                ShopItemProperties shopItemProperties = new ShopItemProperties();
                shopItemProperties.setItemId(si.getId());
                // entity.setEntity(shopItemProperties);
                //EntityWrapper<ShopItemProperties> si=new EntityWrapper();
                List<ShopItemProperties> sips = shopItemPropertiesMapper.selectList(new EntityWrapper<ShopItemProperties>(shopItemProperties));
                List<String> shopItemPropertiesList = shopItemPropertiesMapper.selecetListByItemId(si.getId().toString());
                JSONArray jb=new JSONArray();
                for (String key : shopItemPropertiesList) {
                    JSONArray ja=new JSONArray();
                    item_propertys.put("property_key", key);
                    ShopItemPropertyName SIPN1=new ShopItemPropertyName();
                    SIPN1.setPropertyKey(Integer.parseInt(key));
                    //根据key去拿描述
                    ShopItemPropertyName SIPN=  shopItemPropertyNameMapper.selectOne(SIPN1);
                        item_propertys.put("property_key_desc",SerializeAPI.toUtf8(SIPN.getPropertyName()));
                    List<ShopItemProperties> shopItemProperties1 = shopItemPropertiesMapper.selecetValueByItemId(si.getId().toString(), key);
                    for (ShopItemProperties SIP : shopItemProperties1) {
                        values.put("property_value", SIP.getPropertyValue());
                        values.put("property_desc",SerializeAPI.toUtf8(SIP.getPropertyDesc()));
                        String str12 = JSON.toJSONString(values, SerializerFeature.DisableCircularReferenceDetect);
                        ja.add(JSON.parseObject(str12));
                    }
                    item_propertys.put("values", ja);
                    String str19= JSON.toJSONString(item_propertys, SerializerFeature.DisableCircularReferenceDetect);
                    jb.add(JSON.parseObject(str19));
                }
               /* EntityWrapper<ShopOrderItemProperties> entity1=new EntityWrapper<ShopOrderItemProperties>();*/
                ShopOrderItem shopOrderItem = new ShopOrderItem();
                shopOrderItem.setItemId(si.getId());
               /* entity1.setEntity(shopOrderItemProperties);*/
                Integer j = shopOrderItemMapper.selectCount(new EntityWrapper<ShopOrderItem>(shopOrderItem));
                items.put("order_count", j);
                items.put("item_price", si.getPrice());
                items.put("item_price_discount", DateUtils.getDiscount(si.getPrice(), si.getDiscountRate(), si.getDiscountValue()));
                //获取商品对应价格
                ShopItemPrice shopItemPrice=new ShopItemPrice();
                shopItemPrice.setItemId(si.getId());
                List<ShopItemPrice> lists= shopItemPriceMapper.selectList(new EntityWrapper<ShopItemPrice>(shopItemPrice));
                JSONArray property_prices_key=new JSONArray();
                JSONArray property_prices_value=new JSONArray();
                //JSONArray values=new JSONArray();
                for (ShopItemPrice list:lists){
                 //   property_prices.put(list.getPropertyPath(),list.getPrice());
                    property_prices_key.add(list.getPropertyPath());
                    property_prices_value.add(list.getPrice());
                }
                items.put("property_prices_key",property_prices_key);
                items.put("property_prices_value",property_prices_value);
                items.put("item_propertys", jb);
                String str11 = JSON.toJSONString(items, SerializerFeature.DisableCircularReferenceDetect);
                jc.add(JSON.parseObject(str11));
            }
                content.put("items", jc);
            Jedis client = redisUtil.getJedis();
            String str = "secondary_#2"+"_#"+"*";
            String str1="secondary_#2"+"_#";
            Set<String> ids = client.keys(str);
            for (String id : ids) {
                JSONArray jd=new JSONArray();
                String[] arr = id.split("_#");
                categorys.put("pid", arr[2]);
                Set<String> keys = client.hkeys(str1+arr[2]);
                for (String key: keys) {
                    subs.put("category_id", key);
                    subs.put("category_name",SerializeAPI.toUtf8(client.hget(str1+arr[2], key)));
                    String str2 = JSON.toJSONString(subs, SerializerFeature.DisableCircularReferenceDetect);
                    jd.add(JSON.parseObject(str2));
                }
                categorys.put("subs", jd);
                String str3 = JSON.toJSONString(categorys, SerializerFeature.DisableCircularReferenceDetect);
                je.add(JSON.parseObject(str3));
            }
            content.put("categorys", je);
            redisUtil.returnResource(client);
            param.put("content", content);
            //将搜索记录存入数据库
            ShopSearchHistory ssh=new ShopSearchHistory();
            ssh.setUserId(Long.parseLong(user_id));
            if("".equals(keyword)||keyword==null||keyword.length()<0){
                //分类搜索进入
            }else {
                ssh.setSearchKeyword(keyword);
                shopSearchHistoryMapper.insert(ssh);
            }
            return JSON.toJSONString(param);
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            param.put("content",SerializeAPI.toUtf8("换其他关键搜索"));
            return JSON.toJSONString(param);
        }
    }



    //根据关键词截取所有的字符
    String getKeyWord(String str){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <str.length(); i++) {
            if(sb.toString() != null && sb.toString() !=""){
                sb.append(",");
            }
            sb.append(str.charAt(i));
        }

        String keyword = sb.toString().substring(1);

        keyword = keyword.replaceAll(",", "%");
     return keyword;
    }

}
