package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.ShopBanner;
import com.baomidou.springwind.entity.po.PoBanner;
import com.baomidou.springwind.mapper.ShopBannerMapper;
import com.baomidou.springwind.redis.redisUtil;
import com.baomidou.springwind.service.IShopBannerService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 设置首页banner表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopBannerServiceImpl extends BaseServiceImpl<ShopBannerMapper, ShopBanner> implements IShopBannerService {

    @Override
    public List<PoBanner> selectShopBannerList() {
        return selectShopBannerList();
    }
//获取banner列表
    @Override
    public String getBannerList() {
        //redis取数据\
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject banner=new JSONObject();
        JSONArray  bannerStr1=new JSONArray();
        try {
            String str="banner_list";
            param.put("result","0");
            Jedis client= redisUtil.getJedis();
            //String bannerStr=client.get(str);
            Set<String>sets=client.hkeys(str);
            for (String set:sets){
                String bannerStr= client.hget(str,set);
                ShopBanner shopBanner=JSON.parseObject(bannerStr, ShopBanner.class);
                banner.put("item_id",shopBanner.getItemId());
                banner.put("poster_image_addr",shopBanner.getPosterImageAddr());
                String str1=JSON.toJSONString(banner, SerializerFeature.DisableCircularReferenceDetect);
                bannerStr1.add(JSON.parseObject(str1));
            }
             redisUtil.returnResource(client);
            content.put("banners", bannerStr1);
            param.put("content",content);
            return JSON.toJSONString(param);
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            param.put("content","");
            return JSON.toJSONString(param);
        }
    }
   //后台添加爆款列表到redis
    @Override
    public void addbannerList(ShopBanner shopBanner) {
        String string="banner_list";
        JSONObject sb =new JSONObject();
        try{
                HashMap<String ,String > hm=new HashMap<String, String>();
                Jedis client =redisUtil.getJedis();
                sb.put("item_id",shopBanner.getItemId());
                sb.put("poster_image_addr",shopBanner.getPosterImageAddr());
                String jstring=JSON.toJSONString(sb, SerializerFeature.DisableCircularReferenceDetect);
                hm.put(shopBanner.getId().toString(),jstring);
                client.hmset(string,hm);
                redisUtil.returnResource(client);
        }
        catch (Exception e){
          e.printStackTrace();
        }
    }
    //后台删除banner列表
    @Override
    public void delbannerList(ShopBanner shopBanner) {
        String string="banner_list";
        try{
            HashMap<String ,String > hm=new HashMap<String, String>();
            Jedis client =redisUtil.getJedis();
            client.hdel("banner_list",shopBanner.getId().toString());
            redisUtil.returnResource(client);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

 /*  public static void main(String[] args) throws Exception {
        ShopBanner s=new ShopBanner();
        s.setId(1);
        s.setItemId(2);
        s.setPosterImageAddr("http://img.ciaotalking.com/shopimg/20170412/2bf061ce-be85-45ad-9a4d-21266599b1f8.jpeg");
        //String str=getBanner();
       addbannerList1(s);
    }*/

    @Override
    public List<ShopBanner> selectItemNameBannerList(Map<String,Object> map) {
        return baseMapper.selectShopItemNameBannerList(map);
    }

    @Override
    public List<ShopBanner> selectMultiShopBanner(Map<String,Object> map) {
        return baseMapper.selectMultiShopBanner(map);
    }
/*商品下架关联商品进行修改*/
    @Override
    public void updateState(Integer id) {
        if(null!=id){
            ShopBanner shopBanner =new ShopBanner();
            shopBanner.setItemId(id);
            List<ShopBanner> lists=baseMapper.selectList(new EntityWrapper<ShopBanner>(shopBanner));
            if (lists!=null && !lists.isEmpty()){
            for (ShopBanner list:lists){
                ShopBanner shopBanner1 =new ShopBanner();
                shopBanner1.setState(1);
                shopBanner1.setId(list.getId());
                baseMapper.updateById(shopBanner1);
                delbannerList(list);
             }
            }
        }
    }
/*商品上架后轮播有效*/
    @Override
    public void updateState1(Integer id) {
        ShopBanner shopBanner =new ShopBanner();
        shopBanner.setItemId(id);
        shopBanner.setState(1);
        List<ShopBanner> lists=baseMapper.selectList(new EntityWrapper<ShopBanner>(shopBanner));
        if (lists!=null && !lists.isEmpty()){
            for (ShopBanner list:lists){
                ShopBanner shopBanner1 =new ShopBanner();
                shopBanner1.setState(0);
                shopBanner1.setId(list.getId());
                baseMapper.updateById(shopBanner1);
                addbannerList(list);
            }
        }

    }

    @Override
    public Integer selectMultiShopBannerCount() {
        return baseMapper.selectMultiShopBannerCount();
    }
}
