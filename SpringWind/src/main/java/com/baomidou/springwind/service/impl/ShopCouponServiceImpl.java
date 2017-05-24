package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.ShopCoupon;
import com.baomidou.springwind.entity.ShopCouponCondition;
import com.baomidou.springwind.entity.ShopUserPromo;
import com.baomidou.springwind.mapper.ShopCouponConditionMapper;
import com.baomidou.springwind.mapper.ShopCouponMapper;
import com.baomidou.springwind.mapper.ShopUserPromoMapper;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.service.IShopCouponService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 优惠券表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopCouponServiceImpl extends BaseServiceImpl<ShopCouponMapper, ShopCoupon> implements IShopCouponService {
    @Autowired
    private ShopUserPromoMapper shopUserPromoMapper;
    @Autowired
    private ShopCouponConditionMapper shopCouponConditionMapper;
    @Autowired
    private ShopCouponMapper shopCouponMapper;

    @Override
    public String getCoupon(String user_id, String page_no ,String states) {
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject conditions1=new JSONObject();
        JSONObject ShopCoupon=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        JSONArray conditions=new JSONArray();
        try {
            param.put("result","0");
            int state=Integer.parseInt(states);
            EntityWrapper<ShopUserPromo> entity=new EntityWrapper<ShopUserPromo>();
            ShopUserPromo sor=new ShopUserPromo();
            sor.setUserId(Integer.parseInt(user_id));
            sor.setState(state);
            entity.setEntity(sor);
            int i=shopUserPromoMapper.selectCount(entity);
            //计算最大的页码数
            int maxPage=i/10;
            if (Integer.parseInt(page_no)+1>maxPage){
                content.put("next_page_no",-1);
            }else {
                content.put("next_page_no", Integer.parseInt(page_no) + 1);
            }
            String promo_type="0";
            List<ShopUserPromo> s =shopUserPromoMapper.getShopUserPromoByT(user_id, state,promo_type, Integer.parseInt(page_no)*10);
            for (ShopUserPromo p :s){
                ShopCoupon shopCoupon= shopCouponMapper.selectById(p.getPromoId());
                ShopCoupon.put("promo_id",p.getPromoId());
                ShopCoupon.put("coupon_name",SerializeAPI.toUtf8(shopCoupon.getName()));
                //获取conditions
                ShopCouponCondition shopCouponCondition=new ShopCouponCondition();
                shopCouponCondition.setCouponId(p.getPromoId());
                ShopCouponCondition cc= shopCouponConditionMapper.selectOne(shopCouponCondition);
                conditions1.put("condition_type",cc.getConditionType());
                conditions1.put("condition_desc", SerializeAPI.toUtf8(cc.getConditionDesc()));
                conditions1.put("parameter_first",cc.getParameterFirst());
                conditions1.put("parameter_second",cc.getParameterSecond());
                String str1= JSON.toJSONString(conditions1, SerializerFeature.DisableCircularReferenceDetect);
                jsonArray.add(JSON.parseObject(str1));
                ShopCoupon.put("conditions",jsonArray);
                ShopCoupon.put("grant_date", DateUtils.Date2String(p.getGrantDate()));
                ShopCoupon.put("end_date",DateUtils.Date2String(p.getEndDate()));
                ShopCoupon.put("state",p.getState());
                String str= JSON.toJSONString(ShopCoupon, SerializerFeature.DisableCircularReferenceDetect);
                conditions.add(JSON.parseObject(str));
            }
            content.put("coupons",conditions);
            param.put("content",content);
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
