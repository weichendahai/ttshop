package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.entity.po.PoShopItem;
import com.baomidou.springwind.exception.ShopException;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.redis.redisUtil;
import com.baomidou.springwind.service.IShopBannerService;
import com.baomidou.springwind.service.IShopItemPriceService;
import com.baomidou.springwind.service.IShopItemPropertiesService;
import com.baomidou.springwind.service.IShopItemService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>
 * 商城商品表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopItemServiceImpl extends BaseServiceImpl<ShopItemMapper, ShopItem> implements IShopItemService {
    @Autowired
    private ShopItemMapper shopItemMapper;
    @Autowired
    private ShopOrderItemMapper shopOrderItemMapper;
    @Autowired
    private ShopItemPropertiesMapper shopItemPropertiesMapper;
    @Autowired
    private ShopItemEvaluateMapper shopItemEvaluateMapper;
    @Autowired
    private ShopEvaluateSharedMapper shopEvaluateSharedMapper;
    @Autowired
    private ShopItemUserDistributionMapper shopItemUserDistributionMapper;

    @Autowired
    private ShopItemCategoryMapper shopItemCategoryMapper;
    @Autowired
    private ShopItemPriceMapper shopItemPriceMapper;
    @Autowired
    private ShopItemPropertyNameMapper shopItemPropertyNameMapper;

    @Autowired
    private IShopItemPropertiesService shopItemPropertiesService;

    @Autowired
    private IShopItemPriceService shopItemPriceService;
    @Autowired
    private IShopBannerService shopBannerService;

   //获取商品描述
    @Override
    public String getItemInfo(@Param("item_id") String item_id, @Param("evaluate_user_id") String evaluate_user_id, @Param("shared_user_id") String shared_user_id,String evaluate_shared_id,String user_id) {
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject propertys=new JSONObject();
        JSONObject values=new JSONObject();
        JSONObject distributions=new JSONObject();
        JSONObject distribution_value=new JSONObject();
        JSONArray   jb=new JSONArray();
        try {
            param.put("result","0");
             ShopItem shopItem=shopItemMapper.selectById(item_id);
            content.put("evaluate_user_id",evaluate_user_id);
            content.put("shared_user_id",shared_user_id);
            content.put("item_id",shopItem.getId());
            content.put("item_state",shopItem.getItemState());
            content.put("item_name",URLEncoder.encode(shopItem.getItemName(),"utf-8"));
            content.put("item_big_image_addr",shopItem.getInfoBigImageAddr());
            content.put("item_price_discount", DateUtils.getDiscount(shopItem.getPrice(), shopItem.getDiscountRate(), shopItem.getDiscountValue()));
            //查询产品对应的订单数
            int orderCount=shopOrderItemMapper.selectByItemId(item_id);
            //根据user_id获取对应的值
            List<String> shopItemPropertiesList= shopItemPropertiesMapper.selecetListByItemId(item_id);
            if (null==shopItemPropertiesList || shopItemPropertiesList.size()==0){
                content.put("propertys","");
                content.put("property_prices_key","");
                content.put("property_prices_value","");
            }else {
            for (String key:shopItemPropertiesList){
                propertys.put("property_key",key);
                ShopItemPropertyName SIPN1=new ShopItemPropertyName();
                SIPN1.setPropertyKey(Integer.parseInt(key));
                //根据key去拿描述
                    ShopItemPropertyName SIPN=  shopItemPropertyNameMapper.selectOne(SIPN1);
                    propertys.put("property_key_desc", SerializeAPI.toUtf8(SIPN.getPropertyName()));
                List<ShopItemProperties> shopItemProperties= shopItemPropertiesMapper.selecetValueByItemId(item_id,key);
                JSONArray   ja=new JSONArray();
                for (ShopItemProperties SIP:shopItemProperties){
                    values.put("property_value",SIP.getPropertyValue());
                    values.put("property_desc",URLEncoder.encode(SIP.getPropertyDesc(),"utf-8"));
                    String str12= JSON.toJSONString(values, SerializerFeature.DisableCircularReferenceDetect);
                    ja.add(JSON.parseObject(str12));
                }
                propertys.put("values",ja);
                String str11= JSON.toJSONString(propertys, SerializerFeature.DisableCircularReferenceDetect);
                jb.add(JSON.parseObject(str11));
            }
            //获取描述信息
            content.put("propertys",jb);

            //获取商品对应价格
            ShopItemPrice shopItemPrice=new ShopItemPrice();
            shopItemPrice.setItemId(Integer.parseInt(item_id));
           List<ShopItemPrice> lists= shopItemPriceMapper.selectList(new EntityWrapper<ShopItemPrice>(shopItemPrice));
            JSONArray property_prices_key=new JSONArray();
            JSONArray property_prices_value=new JSONArray();
            for (ShopItemPrice list:lists){
                //   property_prices.put(list.getPropertyPath(),list.getPrice());
                property_prices_key.add(list.getPropertyPath());
                property_prices_value.add(list.getPrice());
            }
            content.put("property_prices_key",property_prices_key);
            content.put("property_prices_value",property_prices_value);
            }
          //  ShopItemEvaluate sie=shopItemEvaluateMapper.selectById(evaluate_user_id);获取评分
            ShopItemEvaluate SIE=new ShopItemEvaluate();
            SIE.setItemId(Integer.parseInt(item_id));
             int count=shopItemEvaluateMapper.selectCount(new EntityWrapper<ShopItemEvaluate>(SIE));
            if (count>0){
                Integer grade =shopItemEvaluateMapper.getGrade(item_id);
                content.put("grade",grade);
            }else {
                content.put("grade",80);
            }

            //获取点评数
            int ecount=shopItemEvaluateMapper.selectCountByuserId(item_id);
            content.put("evaluate_count",ecount);
            //获取分享数量
            List<String> ids=shopItemEvaluateMapper.selectEIds(item_id);
            int scount=0;
            for (String id:ids){
                int j=shopEvaluateSharedMapper.getCountShared(id);
                 scount+=j;
            }
            //获取描述
          List<String > strs= shopItemUserDistributionMapper.selectByItemId(item_id);
            JSONArray   jd=new JSONArray();
            for (String str:strs){
                    distributions.put("distribution_type",str);
                    String  desc=shopItemUserDistributionMapper.selectDesc(str,item_id);
                    distributions.put("distribution_type_desc",URLEncoder.encode(desc,"utf-8"));
                //获取对应的值
                JSONArray   jc=new JSONArray();
                List<ShopItemUserDistribution> subs=shopItemUserDistributionMapper.selectByType(item_id, str);
                for (ShopItemUserDistribution sub:subs){
                    distribution_value.put("distribution_sub",sub.getSub());
                    distribution_value.put("distribution_count",sub.getCount());
                    distribution_value.put("distribution_sub_desc",SerializeAPI.toUtf8(sub.getDistributionSubDesc()));
                    String str11= JSON.toJSONString(distribution_value, SerializerFeature.DisableCircularReferenceDetect);
                    jc.add(JSON.parseObject(str11));
            }
                distributions.put("distribution_value",jc);
                String str15= JSON.toJSONString(distributions, SerializerFeature.DisableCircularReferenceDetect);
                jd.add(JSON.parseObject(str15));
            }
            content.put("distributions",jd);
            content.put("shared_count",scount);
            content.put("item_desc", SerializeAPI.toUtf8(shopItem.getItemEffect()));
            content.put("order_count",orderCount );
           if ("".equals(evaluate_shared_id)||evaluate_shared_id==null){
               content.put("price",shopItem.getPrice());
           }else {
               //过滤他自己分享`
             ShopEvaluateShared SES= shopEvaluateSharedMapper.selectById(evaluate_shared_id);
               if(SES.getUserId()==Long.parseLong(user_id)){
                   content.put("price",shopItem.getPrice());
               }else {
                   int rate=shopItem.getDiscountRate();
                   int value=shopItem.getDiscountValue();
                   int price =shopItem.getPrice();
                   int discount=DateUtils.getDiscount(price,rate,value);
                   content.put("price",discount);
               }
           }
            //获取产品对应peropers
            param.put("content",content);
            return JSON.toJSONString(param);
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            try {
                param.put("content",URLEncoder.encode("系统繁忙","utf-8"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            return JSON.toJSONString(param);
        }
    }
//获取商品详情
    @Override
    public String getItemDesc(String item_id) {
        JSONObject param =new JSONObject();
        JSONObject content=new JSONObject();
        JSONArray info_image_addrs=new JSONArray();
        try {
            param.put("result","0");
            content.put("item_id",item_id);
           ShopItem si= selectById(item_id);
          String[] strs= si.getInfoImageAddrs().split(",");
            for (String str:strs){
                //String str11= JSON.toJSONString(str, SerializerFeature.DisableCircularReferenceDetect);
                info_image_addrs.add(str);
            }
            content.put("info_image_addrs",info_image_addrs);
            param.put("content",content);
            return JSON.toJSONString(param);
        }catch (Exception e){
            param.put("result","1");
            content.put("content", SerializeAPI.toUtf8("系统繁忙"));
            return JSON.toJSONString(param);
        }
}
  //向redis添加商品信息
    @Override
    public void addItemToRedis(ShopItem shopItem) {
  try{
      String str="item_";
     Jedis client= redisUtil.getJedis();
      HashMap<String ,String> itemMap=new HashMap<String,String >();
      itemMap.put(shopItem.getId().toString(),JSON.toJSONString(shopItem));
      client.hmset(str,itemMap);
      redisUtil.returnResource(client);
  }  catch (Exception e){
      e.printStackTrace();
  }
    }
    //从redis删除对应的
    @Override
    public void delItemRedis(String item_id) {
        try{
            String str="item_";
            Jedis client= redisUtil.getJedis();
            client.hdel(str,item_id);
            //删除商品对应的properties
            redisUtil.returnResource(client);
        }  catch (Exception e){
            e.printStackTrace();
        }
    }
    //修改商品信息
    @Override
    public void updateItem(ShopItem shopItem) {
        try{
            String str="item_";
            Jedis client= redisUtil.getJedis();
            client.hdel(str,shopItem.getId().toString());
            HashMap<String ,String> itemMap=new HashMap<String,String >();
            itemMap.put(shopItem.getId().toString(),JSON.toJSONString(shopItem));
            client.hmset(str,itemMap);
            //删除商品对应的properties
            redisUtil.returnResource(client);
        }  catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public Integer selectMultiShopItemCategoryCount(Map<String, Object> map) {
        return baseMapper.selectMultiShopItemCategoryCount(map);
    }

    @Override
    public List<ShopItem> selectMultiShopItemCategoryList(Map<String, Object> map) {
        return baseMapper.selectMultiShopItemCategoryList(map);
    }

    @Override
    public Integer insertShopItemBackKey(ShopItem shopItem) {
        return baseMapper.insert(shopItem);
    }

    @Override
    public Integer updateShopItemBackKey(ShopItem shopItem) {
        return baseMapper.updateById(shopItem);
    }

    @Override
    public List<PoShopItem> selectMultiShopItemList(Map<String, Object> map) {
        List<PoShopItem> shopItems = baseMapper.selectMultiShopItemList(map);
        for(PoShopItem posi:shopItems){
            ShopItemCategory sic = shopItemCategoryMapper.selectById(posi.getItemCategorySub());
            posi.getPoShopItemCategory().getShopItemCategory().setCategoryName(sic.getCategoryName());
        }
        return shopItems;
    }

    @Override
    public List<ShopItem> selectItemByKeywordAllState(String keyword){
        return shopItemMapper.selectByKeywordAllState("%"+keyword+"%",0);
    }

    @Override
    public List<ShopItem> selectItemListByIds(String itemIds) {
        String[] ids = itemIds.trim().split(",");
        List<ShopItem> items = new ArrayList<ShopItem>();
        for(String s:ids){
            ShopItem item = baseMapper.selectById(Integer.parseInt(s));
            if(item!=null){
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public void putItemOn(Integer itemId) throws ShopException{
        ShopItem shopItem = selectById(itemId);
        putItemOn(shopItem);
    }

    @Override
    public void putItemOn(ShopItem shopItem) throws ShopException {
        if (shopItem.getItemState() == StatePutOn){
            throw new ShopException("该商品已经上架");
        }
        //TODO 判断关键数值是否为空 名称，图标，通用价格，描述
        if (StringUtils.isEmpty(shopItem.getItemName())){
            throw new ShopException("名称为空的商品不能上架");
        }
        if (StringUtils.isEmpty(shopItem.getItemIconAddr())){
            throw new ShopException("图标为空的商品不能上架");
        }
        if (StringUtils.isEmpty(shopItem.getItemEffect())){
            throw new ShopException("描述为空的商品不能上架");
        }
        if (shopItem.getPrice() == null || shopItem.getPrice() == 0){
            throw new ShopException("通用价格为空的商品不能上架");
        }

        //TODO 判断规格价格是否正确
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("item_id",shopItem.getId());
        List<ShopItemPrice> itemPrices = shopItemPriceService.selectByMap(map);
        List<ShopItemProperties> itemPropertieses = shopItemPropertiesService.selectByMap(map);

        if (itemPrices ==null && itemPropertieses == null){
            shopItem.setItemState(StatePutOn);
            updateById(shopItem);
            return;
        }

        if ((itemPrices == null && itemPropertieses !=null)|| (itemPrices != null && itemPropertieses == null)){
            throw new ShopException("商品规格与商品规格价格设置不一致");
        }


        Map<Integer,List<Integer>> propertyValueMap = new HashMap<Integer, List<Integer>>();
        List<Integer> propertyKeys = new ArrayList<Integer>();
        for (int i = 0; i < itemPropertieses.size(); i++) {
            ShopItemProperties shopItemProperties = itemPropertieses.get(i);
            Integer key = shopItemProperties.getPropertyKey();
            if (propertyValueMap.containsKey(key)){
                propertyValueMap.get(key).add(shopItemProperties.getPropertyValue());
            }else{
                List<Integer> valueList = new ArrayList<Integer>();
                valueList.add(shopItemProperties.getPropertyValue());
                propertyValueMap.put(key, valueList);
                propertyKeys.add(key);
            }
        }

        //sort
        Collections.sort(propertyKeys);
        for (int i = 0; i < propertyKeys.size(); i++) {
            Integer key = propertyKeys.get(i);
            Collections.sort(propertyValueMap.get(key));
        }

        Set<String> priceSet = new HashSet<String>(itemPrices.size());
        for (int i = 0; i < itemPrices.size(); i++) {
            priceSet.add(itemPrices.get(i).getPropertyPath());
        }

        List<String> pricePath = new ArrayList<String>(itemPrices.size());
        List<String> pricePathTemp;
        for (int i = 0; i < propertyKeys.size(); i++) {
            Integer key = propertyKeys.get(i);
            List<Integer> values =  propertyValueMap.get(key);
            pricePathTemp = new ArrayList<String>(pricePath.size());
            for (int j = 0; j < pricePath.size(); j++) {
                pricePathTemp.add(pricePath.get(j));
            }
            pricePath.clear();
            if (pricePathTemp.size() > 0){
                for (int j = 0; j < pricePathTemp.size(); j++) {
                    for (int k = 0; k < values.size(); k++) {
                        pricePath.add(pricePathTemp.get(j)+"/"+key+":"+values.get(k));
                    }
                }
            }else {
                for (int j = 0; j < values.size(); j++) {
                    pricePath.add(key+":"+values.get(j));
                }
            }
        }
        for (int i = 0; i < pricePath.size(); i++) {
            String priceStr = pricePath.get(i);
            if (!priceSet.contains(priceStr)){
                logger.error("商品规格与商品规格价格设置不一致 : "+priceStr);
                throw new ShopException("商品规格与商品规格价格设置不一致");
            }
        }
        shopItem.setItemState(StatePutOn);
        updateById(shopItem);
        shopBannerService.updateState1(shopItem.getId());
    }

    @Override
    public void putItemOff(Integer itemId) throws ShopException{
        ShopItem shopItem = selectById(itemId);
        putItemOff(shopItem);
    }

    @Override
    public void putItemOff(ShopItem shopItem) throws ShopException {
        if (shopItem.getItemState() == StatePutOff){
            throw new ShopException("该商品已经下架");
        }
        shopItem.setItemState(StatePutOff);
        updateById(shopItem);
        shopBannerService.updateState(shopItem.getId());
    }
}
