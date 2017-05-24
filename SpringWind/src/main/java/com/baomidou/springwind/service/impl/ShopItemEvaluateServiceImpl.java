package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.entity.po.PoShopItemEvaluate;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.redis.redisUtil;
import com.baomidou.springwind.service.IShopItemEvaluateService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户点评表，记录点评信息 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopItemEvaluateServiceImpl extends BaseServiceImpl<ShopItemEvaluateMapper, ShopItemEvaluate> implements IShopItemEvaluateService {
   @Autowired
    private  ShopItemEvaluateMapper  se;
    @Autowired
    private ShopUserMapper user;
    @Autowired
    private ShopEvaluateBonusMapper shopEvaluateBonusMapper;
    @Autowired
    private ShopItemMapper shopItemMapper;
    @Autowired
    private ShopEvaluateSharedMapper shopEvaluateShared;
    @Autowired
    private ShopSharedBonusMapper shopSharedBonusMapper;
    @Autowired
    private ShopEvaluateSharedMapper shopEvaluateSharedMapper;
    @Override
    public String getUserevaluateList(@Param("user_id") String user_id, @Param("page_no") String page_no, @Param("list_type") String list_type, @Param("only_bonus") String only_bonus) {
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject evaluate=new JSONObject();
        JSONArray evaluates=new JSONArray();
        try {
            param.put("result","0");
           if(Integer.parseInt(list_type)==0){
                //获取所有点评记录
                ShopUser shopUser=user.selectById(user_id);
               //获取分页的中页数
               EntityWrapper<ShopItemEvaluate> entity=new EntityWrapper<ShopItemEvaluate>();
               ShopItemEvaluate sor=new ShopItemEvaluate();
               sor.setUserId(Long.parseLong(user_id));
               entity.setEntity(sor);
               int i=selectCount(entity);
               //计算最大的页码数
               int maxPage=i/10;
               if (Integer.parseInt(page_no)+1>maxPage){
                   content.put("next_page_no",-1);
               }else {
                   content.put("next_page_no", Integer.parseInt(page_no) + 1);
               }
                //content.put("next_page_no",Integer.parseInt(page_no)+1);
                content.put("owner_id",shopUser.getId());
                content.put("owner_head_image_addr",shopUser.getHeadImageAddr());
                content.put("owner_nickname",SerializeAPI.toUtf8(shopUser.getNickname()));
                content.put("owner_bonus",shopUser.getEvaluateBonus()+shopUser.getShardBonus());
                content.put("owner_evaluate_count",shopUser.getEvaluateCount());
                content.put("owner_shared_count",shopUser.getShardCount());
                content.put("count",shopUser.getShardCount()+shopUser.getEvaluateCount());
                //获取点评内容和商品信息
               List<ShopItemEvaluate> lists =se.shopItemEvaluateByuserId(user_id, Integer.parseInt(page_no) * 10);
               for (ShopItemEvaluate list:lists){
               //查询点评表
                   //ShopItemEvaluate shopItemEvaluate= selectById(list.getEvaluateId());
                   evaluate.put("evaluate_id",list.getId());
                   evaluate.put("user_id",list.getUserId());
                   ShopUser user1= user.selectById(list.getUserId());
                   evaluate.put("user_head_image_addr",user1.getHeadImageAddr());
                   evaluate.put("user_nickname",SerializeAPI.toUtf8(user1.getNickname()));
                   evaluate.put("user_evaluate_count",user1.getEvaluateCount());
                   evaluate.put("evaluate_image_addr",list.getEvaluateImageAddr());
                   evaluate.put("user_shared_count",user1.getShardCount());
                   evaluate.put("user_date_of_birth", redisUtil.Date2String(user1.getDateOfBirth()));
                   //佣金
                   ShopEvaluateBonus seb =new ShopEvaluateBonus();
                   seb.setUserId(Long.parseLong(user_id));
                   seb.setEvaluateId(list.getId());;
                 List<ShopEvaluateBonus>  sebs=shopEvaluateBonusMapper.selectList(new EntityWrapper<ShopEvaluateBonus>(seb));
                   int cout=0;
                   if (sebs.isEmpty()){
                       evaluate.put("bonus",0);
                   }else {
                       for (ShopEvaluateBonus sS:sebs){
                           cout+= sS.getBonus();
                           evaluate.put("bonus",cout);
                       }
                   }
                ShopItem shopItem= shopItemMapper.selectById(list.getItemId());
                   evaluate.put("item_name",SerializeAPI.toUtf8(shopItem.getItemName()));
                   evaluate.put("item_advantage",SerializeAPI.toUtf8(list.getAdvantage()));
                   evaluate.put("item_disadvatage",SerializeAPI.toUtf8(list.getDisadvantage()));
                   evaluate.put("create_date", DateUtils.Date2String(list.getCreateDate()));
                   String str= JSON.toJSONString(evaluate, SerializerFeature.DisableCircularReferenceDetect);
                   evaluates.add(JSON.parseObject(str));
               }
               content.put("evaluates",evaluates);
               param.put("content",content);
               return JSON.toJSONString(param);
            }else{
               //获取所有点评记录
               EntityWrapper<ShopEvaluateShared> entity3=new EntityWrapper<ShopEvaluateShared>();
               ShopEvaluateShared sor=new ShopEvaluateShared();
               sor.setUserId(Long.parseLong(user_id));
               entity3.setEntity(sor);
               int i=shopEvaluateShared.selectCount(entity3);
               //计算最大的页码数
               int maxPage=i/10;
               if (Integer.parseInt(page_no)+1>maxPage){
                   content.put("next_page_no",-1);
               }else {
                   content.put("next_page_no", Integer.parseInt(page_no) + 1);
               }
              List<ShopEvaluateShared> shopEvaluateShared1= shopEvaluateShared.getInfoByUserId(user_id,Integer.parseInt(page_no)*10);
               for (ShopEvaluateShared ses:shopEvaluateShared1){
                   ShopItemEvaluate shopItemEvaluate=se.selectById(ses.getEvaluateId());
                   ShopUser shopUser=user.selectById(shopItemEvaluate.getUserId());
                   content.put("owner_id",shopUser.getId());
                   content.put("owner_head_image_addr",shopUser.getHeadImageAddr());
                   content.put("owner_nickname",SerializeAPI.toUtf8(shopUser.getNickname()));
                   content.put("owner_bonus",shopUser.getEvaluateBonus()+shopUser.getShardBonus());
                   content.put("owner_evaluate_count",shopUser.getEvaluateCount());
                   content.put("owner_shared_count",shopUser.getShardCount());
                   content.put("count",shopUser.getShardCount()+shopUser.getEvaluateCount());
                     // List<ShopEvaluateBonus> lists =shopEvaluateBonus.ShopEvaluateBonusByuserId(user_id);
                       //查询点评表
                      // ShopItemEvaluate shopItemEvaluates = selectById(ses.getEvaluateId());
                       evaluate.put("evaluate_id",shopItemEvaluate.getId());
                       evaluate.put("evaluate_shared_id",shopItemEvaluate.getId());
                       evaluate.put("user_id",shopItemEvaluate.getUserId());
                       ShopUser user1= user.selectById(shopItemEvaluate.getUserId());
                       evaluate.put("user_head_image_addr",shopUser.getHeadImageAddr());
                       evaluate.put("user_nickname",SerializeAPI.toUtf8(user1.getNickname()));
                       evaluate.put("user_evaluate_count",shopUser.getEvaluateCount());
                       evaluate.put("user_shared_count",shopUser.getShardCount());
                       evaluate.put("user_date_of_birth",redisUtil.Date2String(shopUser.getDateOfBirth()));
                   ShopSharedBonus seb =new ShopSharedBonus();
                   seb.setUserId(Long.parseLong(user_id));
                   seb.setSharedId(ses.getId());;
                   ShopSharedBonus sebs=shopSharedBonusMapper.selectOne(seb);
                   if(sebs==null){
                       evaluate.put("bonus",0);
                   }else {
                       evaluate.put("bonus", sebs.getBonus());
                   }
                       evaluate.put("bonus",shopUser.getEvaluateBonus()+shopUser.getShardBonus() );
                       ShopItem shopItem= shopItemMapper.selectById(shopItemEvaluate.getItemId());
                       evaluate.put("item_name",SerializeAPI.toUtf8(shopItem.getItemName()));
                       evaluate.put("item_icon_addr",shopItem.getItemIconAddr());
                       evaluate.put("item_advantage",SerializeAPI.toUtf8(shopItemEvaluate.getAdvantage()));
                       evaluate.put("evaluate_image_addr",shopItemEvaluate.getEvaluateImageAddr());
                       evaluate.put("item_disadvatage",SerializeAPI.toUtf8(shopItemEvaluate.getDisadvantage()));
                       evaluate.put("create_date",DateUtils.Date2String(shopItemEvaluate.getCreateDate()));
                     String str= JSON.toJSONString(evaluate, SerializerFeature.DisableCircularReferenceDetect);
                   evaluates.add(JSON.parseObject(str));
               //获取点评内容和商品信息
               }
               content.put("evaluates",evaluates);
               param.put("content",content);
               return JSON.toJSONString(param);

            }
            //根据用户id获得多个点评记录
           /* String jsonString = JSON.toJSONString(param);
            return jsonString;*/
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
    @Override
    public String addEvaluate(String user_id, String item_id, String item_advantage, String item_disadvatage, String evaluate_image_addr, String evaluate_vedio_addr, String grade, String other_item_ids) {
       JSONObject param=new JSONObject();
        try {
            //插入一条点评揭露
            ShopItemEvaluate se=new ShopItemEvaluate();
             se.setItemId(Integer.parseInt(item_id.trim()));
             se.setUserId(Long.parseLong(user_id.trim()));
             se.setAdvantage(item_advantage);
            se.setDisadvantage(item_disadvatage);
            se.setEvaluateImageAddr(evaluate_image_addr);
            se.setOtherItemIds(other_item_ids);
            se.setEvaluateState(0);
         //   se.setOtherItemSecond(other_item_name);
            se.getEvaluateVedioAddr();
            //se.setOtherItemThird(other_item_icon);
            se.setGrade(Integer.parseInt(grade));
            //时间格式
            Date date=new Date();
            se.setCreateDate(date);
            insert(se);
            //同时向用户表增加记录
            ShopUser su1=user.selectById(user_id);
            ShopUser su=new ShopUser();
            su.setId(Long.parseLong(user_id));
            su.setEvaluateCount(su1.getEvaluateCount()+1);
            user.updateById(su);
            param.put("result","0");
            param.put("evaluate_id",se.getId());
           //param.put("evaluate_shared_id",ses.getId());
            return JSON.toJSONString(param);
        } catch (Exception e) {
            e.printStackTrace();
            param.put("result","1");
            return JSON.toJSONString(param);
        }
    }

    @Override
    public List<ShopItemEvaluate> selectMultiShopItemEvaluateList(Map<String,Object> map) {

        return baseMapper.selectMultiShopItemEvaluateList(map);
    }

    @Override
    public Integer selectMultiShopItemEvaluateCount(Map<String,Object> map) {
        return baseMapper.selectMultiShopItemEvaluateCount(map);
    }

    @Override
    public List<PoShopItemEvaluate> selectEvaluateListAll(Map<String, Object> map) {
        return baseMapper.selectEvaluateListAll(map);
    }

    @Override
    public Integer selectEvaluateListAllCount(Map<String, Object> map) {
        return baseMapper.selectEvaluateListAllCount(map);
    }

    //获取商品评价列表

    @Override
    public String getItemevaluateList(String item_id,String page_no) {
        JSONObject param = new JSONObject();
        JSONObject content = new JSONObject();
        JSONObject evaluate = new JSONObject();
        JSONArray jsonArray =  new JSONArray();
        try {
         ShopItemEvaluate sie=new ShopItemEvaluate();
            sie.setItemId(Integer.parseInt(item_id));
            int i=se.selectCount(new EntityWrapper<ShopItemEvaluate>(sie));
            int maxPage=i/10;
            if (Integer.parseInt(page_no)+1>maxPage){
                content.put("next_page_no",-1);
            }else {
                content.put("next_page_no", Integer.parseInt(page_no) + 1);
            }
            param.put("result","0" );
            List<ShopUserEvaluate> valuatelists  =se.getItemevaluateList(item_id,Integer.parseInt(page_no)*10);
            content.put("item_id",item_id);
            for (ShopUserEvaluate valuatelist:valuatelists){
                evaluate.put("user_id",valuatelist.getUserId());
              ShopItem si=  shopItemMapper.selectById(item_id);
                evaluate.put("item_name",SerializeAPI.toUtf8(si.getItemName()));
                evaluate.put("evaluate_id",valuatelist.getEvaluateId());
                evaluate.put("user_head_image_addr",valuatelist.getUserHeadImageAdd());
                evaluate.put("user_nickname", SerializeAPI.toUtf8(valuatelist.getUserNickname()));
                evaluate.put("user_evaluate_count",valuatelist.getUserEvaluateCoun());
                evaluate.put("user_shared_count",valuatelist.getUserSharedCount());
                evaluate.put("user_date_of_birth",DateUtils.Date2String(redisUtil.String2Date(valuatelist.getUserDateOfBirth())));
                evaluate.put("grade",valuatelist.getGrade());
                evaluate.put("item_advantage",SerializeAPI.toUtf8(valuatelist.getItemAdvantage()));
                evaluate.put("item_disadvatage",SerializeAPI.toUtf8(valuatelist.getItemDisadvatage()));
                evaluate.put("create_date", DateUtils.Date2String(valuatelist.getCreateDate()));
                evaluate.put("evaluate_image_addr",valuatelist.getEvaluateImageAddr());
                String str= JSON.toJSONString(evaluate, SerializerFeature.DisableCircularReferenceDetect);
                jsonArray.add(JSON.parseObject(str));
            }
            content.put("evaluates",jsonArray);
            param.put("content",content);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
/*获取点评详情
* */
    @Override
    public String getEvaluateInfo(String user_id, String evaluate_id,String evaluate_shared_id) {
            JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject other=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            param.put("result","0");
            //根据id查询
            ShopItemEvaluate shopItemEvaluate = selectById(evaluate_id);
            if(shopItemEvaluate==null){
                content.put("content",SerializeAPI.toUtf8("快来点评吧"));
            }else {
                content.put("owner_id", shopItemEvaluate.getUserId());
                content.put("evaluate_id", shopItemEvaluate.getId());
              //  ShopEvaluateShared SES=shopEvaluateShared.selectById(evaluate_shared_id);
                //查询是否是原创
                content.put("evaluate_shared_id", evaluate_shared_id);
                content.put("item_id", shopItemEvaluate.getItemId());
                //根据itemId差Item
                ShopItem shopItem = shopItemMapper.selectById(shopItemEvaluate.getItemId());
                content.put("item_name", SerializeAPI.toUtf8(shopItem.getItemName()));
                content.put("item_icon", shopItem.getItemIconAddr());
                content.put("item_price_discount", DateUtils.getDiscount(shopItem.getPrice(), shopItem.getDiscountRate(), shopItem.getDiscountValue()));
                if ("".equals(evaluate_shared_id)||evaluate_shared_id==null){
                    content.put("item_price",shopItem.getPrice());
                }else {
                    //过滤他自己分享
                    ShopEvaluateShared SES= shopEvaluateSharedMapper.selectById(evaluate_shared_id);
                    if(SES.getUserId()==Long.parseLong(user_id)){
                        content.put("item_price",shopItem.getPrice());
                    }else {
                        int rate=shopItem.getDiscountRate();
                        int value=shopItem.getDiscountValue();
                        int price =shopItem.getPrice();
                        int discount=DateUtils.getDiscount(price,rate,value);
                        content.put("item_price",discount);
                    }
                }
                //content.put("item_price", shopItem.getPrice());
                //ShopOrderItem orderItem= .getItemOrderByItemid(Long .parseLong(shopItemEvaluate.getItemId().toString()));
            /*content.put("item_property_key", orderItem.getPropertyKey());
            content.put("item_property_value",orderItem.getPropertyValue());*/
                //获取相关产品
                if ("".equals(shopItemEvaluate.getOtherItemIds())||shopItemEvaluate.getOtherItemIds()==null){
                }else {
                String[] arr = shopItemEvaluate.getOtherItemIds().split(",");
                for (String str : arr) {
                    ShopItem si = shopItemMapper.selectById(str);
                    other.put("other_item_name", SerializeAPI.toUtf8(si.getItemName()));
                    other.put("other_item_price",si.getPrice());
                    other.put("other_item_icon_addr", si.getItemIconAddr());
                    other.put("other_item_id", si.getId());
                    String str1 = JSON.toJSONString(other, SerializerFeature.DisableCircularReferenceDetect);
                    jsonArray.add(JSON.parseObject(str1));
                }
                content.put("getOtherItemIds", jsonArray);
                }
                content.put("item_advantage", SerializeAPI.toUtf8(shopItemEvaluate.getAdvantage()) );
                content.put("item_disadvatage", SerializeAPI.toUtf8(shopItemEvaluate.getDisadvantage()));
                content.put("evaluate_image_addr", shopItemEvaluate.getEvaluateImageAddr());
                content.put("evaluate_vedio_addr", shopItemEvaluate.getEvaluateVedioAddr());
                content.put("grade", shopItemEvaluate.getGrade());
                content.put("create_date", DateUtils.Date2String(shopItemEvaluate.getCreateDate()));
            }
            //根据用户信息获取对应的数据
           ShopUser su= user.selectById(Long.parseLong(user_id));
            content.put("nickname",SerializeAPI.toUtf8(su.getNickname()));
            content.put("date_of_birth",DateUtils.Date2String(su.getDateOfBirth()));
            content.put("head_image_addr",su.getHeadImageAddr());
            ShopEvaluateBonus seb =new ShopEvaluateBonus();
            seb.setUserId(Long.parseLong(user_id));
            seb.setEvaluateId(Long.parseLong(evaluate_id));;
            List<ShopEvaluateBonus>  sebs=shopEvaluateBonusMapper.selectList(new EntityWrapper<ShopEvaluateBonus>(seb));
            int cout=0;
            if (sebs.isEmpty()){
                content.put("bonus",0);
            }else {
                for (ShopEvaluateBonus sS:sebs){
                    cout+= sS.getBonus();
                    content.put("bonus",cout);
                }
            }
                param.put("content", content);
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
