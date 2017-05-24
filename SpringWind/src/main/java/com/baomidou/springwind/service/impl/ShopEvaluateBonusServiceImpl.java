package com.baomidou.springwind.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.entity.po.PoShopEvaluateBonus;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.redis.redisUtil;
import com.baomidou.springwind.service.IShopEvaluateBonusService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 点评佣金表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
@Service
public class ShopEvaluateBonusServiceImpl extends BaseServiceImpl<ShopEvaluateBonusMapper, ShopEvaluateBonus> implements IShopEvaluateBonusService {
     @Autowired
private ShopEvaluateSharedMapper shopEvaluateSharedMapper;
    @Autowired
private ShopItemEvaluateMapper shopItemEvaluateMapper;
    @Autowired
private ShopEvaluateBonusMapper shopEvaluateBonusMapper;
    @Autowired
private ShopItemMapper shopItemMapper;
    @Autowired
private ShopUserMapper shopUserMapper;
    @Autowired
private ShopSharedBonusMapper shopSharedBonusMapper;
    @Autowired
private ShopOrderMapper shopOrderMapper;
    public List<PoShopEvaluateBonus> selectMultiShopEvaluateSharedList(Map<String, Object> map) {
        return baseMapper.selectMultiShopEvaluateSharedList(map);
    }
/*
* 收货后提交佣金的计算放发*/
    @Override
    public void getShopEvaluateBonus(String order_id) {
     Jedis client= redisUtil.getJedis();
        String evaluate_shared_id=client.get("shopbouns_"+order_id);
        client.del("shopbouns_"+order_id);
        redisUtil.returnResource(client);
       ShopOrder SO=shopOrderMapper.selectById(order_id);
        String user_id=SO.getUserId().toString();
        ShopEvaluateShared ses=shopEvaluateSharedMapper.selectById(evaluate_shared_id);
        ShopItemEvaluate sie=new ShopItemEvaluate();
        sie.setId(ses.getEvaluateId());
        sie.setUserId(ses.getUserId());
        int i=shopItemEvaluateMapper.selectCount(new EntityWrapper<ShopItemEvaluate>(sie));
        ShopItemEvaluate Siem=shopItemEvaluateMapper.selectById(ses.getEvaluateId());
        ShopItem si=shopItemMapper.selectById(Siem.getItemId());
        //判断是点评还是分享
        if (i>0){
            ShopEvaluateBonus seb=new ShopEvaluateBonus();
            seb.setEvaluateId(Siem.getId());
            seb.setOrderId(Long.parseLong(order_id));
            seb.setUserId(Siem.getUserId());
            seb.setItemId(Siem.getItemId());
            seb.setCreateDate((new Date()));

            int bonEs=0;
            if (si.getEvaluateBonusValue() != null && si.getEvaluateBonusValue() > 0) {
                bonEs=si.getEvaluateBonusValue();
            } else if (si.getEvaluateBonusRate() != null && si.getEvaluateBonusRate() > 0) {
                if (si.getPrice() * si.getEvaluateBonusRate() % 100 < 50) {
                    bonEs=si.getPrice()*si.getEvaluateBonusRate()/100;
                } else {
                    bonEs=si.getPrice()*si.getEvaluateBonusRate()/100+1;
                }
            } else {
                bonEs=0;
            }
            seb.setBonus(bonEs);
            shopEvaluateBonusMapper.insert(seb);
            ShopUser su1=shopUserMapper.selectById(user_id);
            ShopUser su=new ShopUser();
            su.setId(Long.parseLong(user_id));
            su.setEvaluateBonus(su1.getEvaluateBonus()+bonEs);
            shopUserMapper.updateById(su);
        }else {
            int Share=0;
            ShopSharedBonus ssb=new ShopSharedBonus();
            ssb.setSharedId(Long.parseLong(evaluate_shared_id));
            ssb.setOrderId(Long.parseLong(order_id));
            ssb.setUserId(ses.getUserId());
            ssb.setItemId((long)Siem.getItemId());
            ssb.setCreateDate(new Date());
            if (si.getEvaluateBonusValue() != null && si.getEvaluateBonusValue() > 0) {
                Share=si.getEvaluateBonusValue();
            } else if (si.getEvaluateBonusRate() != null && si.getEvaluateBonusRate() > 0) {
                if (si.getPrice() * si.getEvaluateBonusRate() % 100 < 50) {
                    Share=si.getPrice()*si.getEvaluateBonusRate()/100;
                } else {
                    Share=si.getPrice()*si.getEvaluateBonusRate()/100+1;
                }
            } else {
                Share=0;
            }
            ssb.setBonus(Share);
            shopSharedBonusMapper.insert(ssb);;
            ShopUser su1=shopUserMapper.selectById(user_id);
            ShopUser su=new ShopUser();
            su.setId(Long.parseLong(user_id));
            su.setShardBonus(su1.getShardBonus()+Share);
            shopUserMapper.updateById(su);
        }
    }
}
