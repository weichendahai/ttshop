package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.ShopGiftCard;
import com.baomidou.springwind.entity.ShopUserPromo;
import com.baomidou.springwind.mapper.ShopGiftCardMapper;
import com.baomidou.springwind.mapper.ShopUserPromoMapper;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.service.IShopGiftCardService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 红包表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopGiftCardServiceImpl extends BaseServiceImpl<ShopGiftCardMapper, ShopGiftCard> implements IShopGiftCardService {
    @Autowired
    private ShopUserPromoMapper shopUserPromoMapper;
   /* @Autowired
    private ShopUserPromoMapper shopGiftCardMapper;*/

    @Override
    public String getGiftCard(String user_id, String page_no,String state) {
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject giftCards=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            int states=Integer.parseInt(state);
            param.put("result","0");
            EntityWrapper<ShopUserPromo> entity=new EntityWrapper<ShopUserPromo>();
            ShopUserPromo sor=new ShopUserPromo();
            sor.setUserId(Integer.parseInt(user_id));
            sor.setState(states);
            entity.setEntity(sor);
            int i=shopUserPromoMapper.selectCount(entity);
            //计算最大的页码数
            int maxPage=i/10;
            if (Integer.parseInt(page_no)+1>maxPage){
                content.put("next_page_no",-1);
            }else {
                content.put("next_page_no", Integer.parseInt(page_no) + 1);
            }
            String promo_type="1";
            List<ShopUserPromo> s =shopUserPromoMapper.getShopUserPromoByT(user_id, states, promo_type,Integer.parseInt(page_no)*10);
            for (ShopUserPromo p :s){
                ShopGiftCard   shopGiftCard  =selectById(p.getPromoId());
                giftCards.put("promo_id",p.getPromoId());
                giftCards.put("gift_card_name", SerializeAPI.toUtf8(shopGiftCard.getName()));
                giftCards.put("gift_card_balance",shopGiftCard.getBalance());
                giftCards.put("grant_date", DateUtils.Date2String(p.getGrantDate()));
                giftCards.put("end_date",DateUtils.Date2String(p.getEndDate()));
                giftCards.put("state",p.getState());
                String str= JSON.toJSONString(giftCards, SerializerFeature.DisableCircularReferenceDetect);
                jsonArray.add(JSON.parseObject(str));
            }
            content.put("gift_cards",jsonArray);
            param.put("content",content);
            return JSON.toJSONString(param);
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }
}
