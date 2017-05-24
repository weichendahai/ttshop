package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.ShopBonusPayment;
import com.baomidou.springwind.entity.ShopUser;
import com.baomidou.springwind.entity.po.PoShopBonusPayment;
import com.baomidou.springwind.mapper.ShopBonusPaymentMapper;
import com.baomidou.springwind.mapper.ShopUserMapper;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.redis.redisUtil;
import com.baomidou.springwind.service.IShopBonusPaymentService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 佣金提现表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
@Service
public class ShopBonusPaymentServiceImpl extends BaseServiceImpl<ShopBonusPaymentMapper, ShopBonusPayment> implements IShopBonusPaymentService {
    @Autowired
    private ShopUserMapper shopUserMapper;
    @Autowired
    private ShopBonusPaymentMapper shopBonusPaymentMapper;
    @Override
    public String getBonus(String user_id, String page_no) {
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject giftCards=new JSONObject();
        JSONObject payments1=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            ShopUser user= shopUserMapper.selectById(user_id);
            param.put("result","0");
            content.put("default_balance",user.getShardBonus()+user.getEvaluateBonus());
            //分页
            EntityWrapper<ShopBonusPayment> entity=new EntityWrapper<ShopBonusPayment>();
            ShopBonusPayment sor=new ShopBonusPayment();
            sor.setUserId(Long.parseLong(user_id));
            entity.setEntity(sor);
            int i=shopBonusPaymentMapper.selectCount(entity);
            //计算最大的页码数
            int maxPage=i/10;
            if (Integer.parseInt(page_no)+1>maxPage){
                content.put("next_page_no",-1);
            }else {
                content.put("next_page_no", Integer.parseInt(page_no) + 1);
            }
            List<ShopBonusPayment> payments =shopBonusPaymentMapper.selectByUserId(user_id,Integer.parseInt(page_no)*10);
            for (ShopBonusPayment p :payments){
                payments1.put("payment_id",p.getId());
                payments1.put("total",p.getTotal());
                payments1.put("create_date", DateUtils.Date2String(p.getCreateDate()));
                if (p.getState()==0){
                    payments1.put("payment_date",null);
                }else {
                    payments1.put("payment_date", DateUtils.Date2String(p.getPaymentDate()));
                }
                payments1.put("state",p.getState());
                String str= JSON.toJSONString(payments1, SerializerFeature.DisableCircularReferenceDetect);
                jsonArray.add(JSON.parseObject(str));
            }
            content.put("payments",jsonArray);
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

    @Override
    public String requestBonusPayment(String user_id) {
        JSONObject param=new JSONObject();
        JSONObject content=new JSONObject();
        JSONObject giftCards=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        //插入一条语句
        try {
            ShopUser user= shopUserMapper.selectById(user_id);
            ShopBonusPayment bp=new ShopBonusPayment();
            bp.setUserId(Long.parseLong(user_id));
            bp.setTotal(user.getShardBonus() + user.getEvaluateBonus());
            bp.setCreateDate(new Date());
            bp.setPaymentDate(null);
            bp.setState(0);
            shopBonusPaymentMapper.insertBonusPayment(bp);
            param.put("result", "0");
            giftCards.put("payment_id",bp.getId());
            giftCards.put("total",bp.getTotal());
            giftCards.put("create_date",DateUtils.Date2String(bp.getCreateDate()));
            giftCards.put("payment_date","");
            giftCards.put("state",bp.getState());
            String str= JSON.toJSONString(giftCards, SerializerFeature.DisableCircularReferenceDetect);
            jsonArray.add(JSON.parseObject(str));
            content.put("payments", jsonArray);
            param.put("content",content);
            ShopUser su=new ShopUser();
            su.setShardBonus(0);
            su.setEvaluateBonus(0);
            su.setId(Long.parseLong(user_id));
            shopUserMapper.updateById(su);
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }
    }

    @Override
    public List<PoShopBonusPayment> selectMultiBonusPaymentList(Map<String, Object> map) {
        return baseMapper.selectMultiBonusPaymentList(map);
    }

    @Override
    public Integer selectMultiBonusPaymentCount(Map<String, Object> map) {
        return baseMapper.selectMultiBonusPaymentCount(map);
    }

    /**
     * 处理申请提现
     * @param bonusId
     * @return
     */
    @Override
    public boolean updateShopBonusPaymentState(Integer bonusId) {
        ShopBonusPayment payment = baseMapper.selectById(bonusId);
        ShopUser user = shopUserMapper.selectById(payment.getUserId());
        Integer tem  = user.getShardBonus()+user.getEvaluateBonus();
        Integer payTotal  = payment.getTotal();
        int u=0,p=0;
        if(tem!=0&&payTotal!=0){
            int result = user.getEvaluateBonus()-payTotal;
            if(result<=0){
                user.setEvaluateBonus(0);
                result = user.getShardBonus()+result;
                if(result<=0){
                    result=0;
                }
                user.setShardBonus(result);
                payment.setState(1);
                payment.setPaymentDate(new Date());
                u=shopUserMapper.updateById(user);
                p=baseMapper.updateById(payment);
            }else {
               user.setEvaluateBonus(result);
                payment.setState(1);
                payment.setPaymentDate(new Date());
                u=shopUserMapper.updateById(user);
                p=baseMapper.updateById(payment);
            }
            if(tem<payTotal){
                return false;
            }
        }
        if(2==(u+p)){
            return true;
        }
        return false;
    }
}
