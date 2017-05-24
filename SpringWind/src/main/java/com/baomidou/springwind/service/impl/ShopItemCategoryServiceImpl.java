package com.baomidou.springwind.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.ShopItemCategory;
import com.baomidou.springwind.mapper.ShopItemCategoryMapper;
import com.baomidou.springwind.redis.redisUtil;
import com.baomidou.springwind.service.IShopItemCategoryService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类表，配合商品使用 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopItemCategoryServiceImpl extends BaseServiceImpl<ShopItemCategoryMapper, ShopItemCategory> implements IShopItemCategoryService {
    @Autowired
     private ShopItemCategoryMapper shopItemCategoryMapper;
    //redis添加二级页面的分类
    @Override
    public void addCategory(ShopItemCategory shopItemCategory) {
        try{
            String  str="secondary_#"+shopItemCategory.getCategoryLevel()+"_#"+shopItemCategory.getPid();
            Map<String,String > map =new HashMap<String, String>();
            Jedis client= redisUtil.getJedis();
            //String str1=shopItemCategory.getCategoryName();
            String string =shopItemCategory.getId().toString();
            map.put(string ,shopItemCategory.getCategoryName());
            client.hmset(str,map);
            redisUtil.returnResource(client);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    //修改redis二级目录
    public void delCategory(ShopItemCategory shopItemCategory) {
        try{
            String  str="secondary_#"+shopItemCategory.getCategoryLevel()+"_#"+shopItemCategory.getPid();
            Jedis client= redisUtil.getJedis();
            Map<String,String > map =new HashMap<String, String>();
            String string =shopItemCategory.getId().toString();
            client.hdel(str, string);
            map.put(string ,shopItemCategory.getCategoryName());
            client.hmset(str,map);
            redisUtil.returnResource(client);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //删除redis二级目录
    public void updateCategory(ShopItemCategory shopItemCategory) {
        try{
            String  str="secondary_#"+shopItemCategory.getCategoryLevel()+"_#"+shopItemCategory.getPid();
            Jedis client= redisUtil.getJedis();
            String string =shopItemCategory.getId().toString();
            client.hdel(str,string);
            redisUtil.returnResource(client);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
/*   //test添加二级目录到redis
    public static void main(String[] args) {
        //添加一条假数据
        ShopItemCategory shopItemCategory=new ShopItemCategory();
        ShopItemCategory shopItemCategory1=new ShopItemCategory();
        shopItemCategory.setId(9);
        shopItemCategory1.setId(10);
        shopItemCategory.setCategoryName("欧莱雅");
        shopItemCategory1.setCategoryName("美妆");
        shopItemCategory.setCategoryLevel(2);
        shopItemCategory1.setCategoryLevel(2);
        shopItemCategory.setPid(1);
        shopItemCategory1.setPid(1);
        addCategory1(shopItemCategory);
        addCategory1(shopItemCategory1);
    }*/

    @Override
    public List<ShopItemCategory> selectMultiShopItemCategoryList(Map<String, Object> map) {
        return baseMapper.selectMultiShopItemCategoryList(map);
    }

    @Override
    public Integer selectMultiShopItemCategoryCount(Map<String, Object> map) {
        return baseMapper.selectMultiShopItemCategoryCount(map);
    }

    @Override
    public void rediserji() {
        List<ShopItemCategory> lists=shopItemCategoryMapper.selectList(new EntityWrapper<ShopItemCategory>(new ShopItemCategory()));
        for (ShopItemCategory List:lists){
            addCategory(List);
        }

    }
}
