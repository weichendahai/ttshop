package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.entity.po.PoShopEvaluateShared;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.service.IShopEvaluateSharedService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 点评的分享记录表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopEvaluateSharedServiceImpl extends BaseServiceImpl<ShopEvaluateSharedMapper, ShopEvaluateShared> implements IShopEvaluateSharedService {
   //点评和分享榜单
    @Autowired
    private ShopUserMapper shopUserMapper;
    @Autowired
    private ShopEvaluateBonusMapper shopEvaluateBonusMapper;
    @Autowired
    private ShopSharedBonusMapper shopSharedBonusMapper;
    @Autowired
    private ShopEvaluateSharedMapper shopEvaluateSharedMapper;
    @Autowired
    private ShopItemEvaluateMapper shopItemEvaluateMapper;
    @Autowired
    private ShopItemMapper shopItemMapper;
    @Override
    public String evaluateRanking(String ranking_type, String cycle, String season_no, String user_id,String page_no) {
        JSONObject param=new JSONObject();
        /*
     ranking_type	榜单类型，0点评，1分享
     cycle	0表示周，1表示月
     */
        try {
            JSONObject content=new JSONObject();
            JSONObject myself=new JSONObject();
            JSONObject listitems=new JSONObject();
            JSONArray ja=new JSONArray();
            String  startDate=null;
            String  endDate=null;
            param.put("result","0");
            if (Integer.parseInt(ranking_type)==0) {
                if (Integer.parseInt(cycle)==0){
                    //周榜单按照佣金排序
                    if (Integer.parseInt(season_no)>0){
                        startDate= DateUtils.getMONDAY();
                        endDate= DateUtils.getSUNDAY();
                    }else {
                        startDate= DateUtils.getPvMONDAY();
                        endDate= DateUtils.getPvSUNDAY();
                    }

                }else {
                    //月榜单
                    if (Integer.parseInt(season_no)>0){
                        Map<String,String>  dateMap=DateUtils.convertMonthByDate(new Date());
                        startDate= dateMap.get("first");
                        endDate=dateMap.get("last");
                    }else {
                        Map<String,String>  dateMap=DateUtils.convertPvMonthByDate(new Date());
                        startDate= dateMap.get("first");
                        endDate=dateMap.get("last");
                    }
                   // List<ShopEvaluateBonus> sbs=shopEvaluateBonusMapper.selectRank(startDate,endDate);
                  /*  List<ShopEvaluateBonus> sb=shopEvaluateBonusMapper.selectRank();
                    ShopUser su=shopUserMapper.selectById(user_id);*/
                }
                String str="%Y-%m-%d %T";
                Integer i=shopEvaluateBonusMapper.getCount(startDate,endDate,str);
                int maxPage=i/10;
                if (Integer.parseInt(page_no)+1>maxPage){
                    content.put("next_page_no",-1);
                }else {
                    content.put("next_page_no", Integer.parseInt(page_no) + 1);
                }
                List<ShopEvaluateBonus> sbs=shopEvaluateBonusMapper.selectRank(startDate,endDate,Integer.parseInt(page_no)*10,str);
                for (ShopEvaluateBonus sb:sbs) {
                    if (Long.parseLong(user_id) == sb.getUserId()) {
                        ShopUser su = shopUserMapper.selectById(user_id);
                        myself.put("user_id", su.getId());
                        myself.put("rank_no", sb.getId());
                        myself.put("bonus", sb.getBonus());
                        myself.put("nickname", SerializeAPI.toUtf8(su.getNickname()) );
                        myself.put("head_icon", su.getHeadImageAddr());
                        content.put("myself",myself);
                    }
                        ShopUser su = shopUserMapper.selectById(sb.getUserId());
                        listitems.put("user_id", su.getId());
                        listitems.put("rank_no", sb.getId());
                        listitems.put("bonus", sb.getBonus());
                        listitems.put("nickname",SerializeAPI.toUtf8(su.getNickname()));
                        listitems.put("head_icon", su.getHeadImageAddr());
                    String str12= JSON.toJSONString(listitems, SerializerFeature.DisableCircularReferenceDetect);
                    ja.add(JSON.parseObject(str12));
                }
            }else {
               if (Integer.parseInt(cycle)==0){
                   if (Integer.parseInt(season_no)>0){
                       startDate= DateUtils.getMONDAY();
                       endDate= DateUtils.getSUNDAY();
                   }else {
                       startDate= DateUtils.getPvMONDAY();
                       endDate= DateUtils.getPvSUNDAY();
                   }
                    //List<ShopEvaluateBonus> sbs=shopEvaluateBonusMapper.selectRank(startDate,endDate);
                }else {
                   if (Integer.parseInt(season_no)>0){
                       Map<String,String>  dateMap=DateUtils.convertMonthByDate(new Date());
                       startDate= dateMap.get("first");
                       endDate=dateMap.get("last");
                   }else {
                       Map<String,String>  dateMap=DateUtils.convertPvMonthByDate(new Date());
                       startDate= dateMap.get("first");
                       endDate=dateMap.get("last");
                   }
                   //List<ShopSharedBonus> sbs1=shopSharedBonusMapper.selectRank(startDate,endDate);
                }
                int i=shopSharedBonusMapper.getCount(startDate,endDate);
                int maxPage=i/10;
                if (Integer.parseInt(page_no)+1>maxPage){
                    content.put("next_page_no",-1);
                }else {
                    content.put("next_page_no", Integer.parseInt(page_no) + 1);
                }
                List<ShopSharedBonus> sbs1=shopSharedBonusMapper.selectRank(startDate, endDate,Integer.parseInt(page_no)*10);
                for (ShopSharedBonus sb:sbs1) {
                    if (Long.parseLong(user_id) == sb.getUserId()) {
                        ShopUser su = shopUserMapper.selectById(sb.getUserId());
                        myself.put("user_id", su.getId());
                        myself.put("rank_no", sb.getId());
                        myself.put("bonus", sb.getBonus());
                        myself.put("nickname", SerializeAPI.toUtf8(su.getNickname()));
                        myself.put("head_icon", su.getHeadImageAddr());
                        content.put("myself",myself);
                    }
                        ShopUser su = shopUserMapper.selectById(sb.getUserId());
                        listitems.put("user_id", su.getId());
                        listitems.put("rank_no", sb.getId());
                        listitems.put("bonus", sb.getBonus());
                        listitems.put("nickname", SerializeAPI.toUtf8(su.getNickname()));
                        listitems.put("head_icon", su.getHeadImageAddr());
                    String str12= JSON.toJSONString(listitems, SerializerFeature.DisableCircularReferenceDetect);
                    ja.add(JSON.parseObject(str12));
                }
            }
            content.put("listitems",ja);
            param.put("content",content);
            return JSON.toJSONString(param);
        }catch (Exception e){
            e.printStackTrace();
            param.put("result",1);
            param.put("content",SerializeAPI.toUtf8("后台异常"));
         return JSON.toJSONString(param);
        }
    }
    @Override
    public List<PoShopEvaluateShared> selectMultiShopEvaluateShared(Map<String, Object> map) {
        return baseMapper.selectMultiShopEvaluateSharedList(map);
    }

    @Override
    public Integer selectMultiShopEvaluateSharedCount(Map<String, Object> map) {
        return baseMapper.selectMultiShopEvaluateSharedCount(map);
    }
//转发朋友圈
    @Override
    public String sharedEvaluate(String evaluate_id, String user_id) {
       JSONObject param=new JSONObject();
        param.put("result",0);
      ShopItemEvaluate Sie= shopItemEvaluateMapper.selectById(evaluate_id);
        ShopItem Si=shopItemMapper.selectById(Sie.getItemId());
        param.put("item_icon_addr",Si.getItemIconAddr());
        param.put("item_name",SerializeAPI.toUtf8(Si.getItemName()));
        try {
                ShopEvaluateShared ses=new ShopEvaluateShared();
                ses.setEvaluateId(Long.parseLong(evaluate_id));
                ses.setUserId(Long.parseLong(user_id));
                ses.setCreateDate(new Date());
                shopEvaluateSharedMapper.insert(ses);
                param.put("evaluate_shared_id",ses.getId());
            //用户表插入一条记录
           ShopUser su1= shopUserMapper.selectById(user_id);
            ShopUser su=new ShopUser();
            su.setId(su1.getId());
            su.setShardCount(su1.getShardCount()+1);
            shopUserMapper.updateById(su);
            return JSON.toJSONString(param);
        }catch (Exception e){
            e.printStackTrace();
            param.put("result",1);
            return JSON.toJSONString(param);
        }
    }

}
