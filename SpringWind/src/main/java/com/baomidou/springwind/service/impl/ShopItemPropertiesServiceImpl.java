package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.springwind.entity.ShopItemProperties;
import com.baomidou.springwind.mapper.ShopItemPropertiesMapper;
import com.baomidou.springwind.redis.redisUtil;
import com.baomidou.springwind.service.IShopItemPropertiesService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性表，型号，颜色，容量等 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopItemPropertiesServiceImpl extends BaseServiceImpl<ShopItemPropertiesMapper, ShopItemProperties> implements IShopItemPropertiesService {
// 向redis添加proper属性
    @Override
    public void addShopItemPropertiesToRedis(ShopItemProperties shopItemProperties) {
        try {
            String str = "item_properties"+"#"+shopItemProperties.getItemId().toString();
            Jedis client = redisUtil.getJedis();
            HashMap<String, String> itemMap = new HashMap<String, String>();
            itemMap.put(shopItemProperties.getId().toString(), JSON.toJSONString(shopItemProperties));
            client.hmset(str, itemMap);
            redisUtil.returnResource(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //删除一个商品对应的属性
    @Override
    public void delShopItemProperties(ShopItemProperties shopItemProperties) {
        try{

            String str="item_properties"+"#"+shopItemProperties.getItemId().toString();
            Jedis client= redisUtil.getJedis();
            client.hdel(str,shopItemProperties.getId().toString());
            //删除商品对应的properties
            redisUtil.returnResource(client);
        }  catch (Exception e){
            e.printStackTrace();
        }
    }
    //修改一个商品对应的属性
    @Override
    public void updateitem_properties(ShopItemProperties shopItemProperties) {
        try{
            String str="item_properties"+"#"+shopItemProperties.getItemId().toString();
            Jedis client= redisUtil.getJedis();
            client.hdel(str,shopItemProperties.getId().toString());
            HashMap<String, String> itemMap = new HashMap<String, String>();
            itemMap.put(shopItemProperties.getId().toString(), JSON.toJSONString(shopItemProperties));
            client.hmset(str,itemMap);
            //删除商品对应的properties
            redisUtil.returnResource(client);
        }  catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<ShopItemProperties> selectMultiShopItemPropertyList(Map<String, Object> map) {
        return baseMapper.selectMultiShopItemPropertyList(map);
    }

    @Override
    public Integer selectMultiShopItemPropertyCount(Map<String, Object> map) {
        return baseMapper.selectMultiShopItemPropertyCount(map);
    }
}
