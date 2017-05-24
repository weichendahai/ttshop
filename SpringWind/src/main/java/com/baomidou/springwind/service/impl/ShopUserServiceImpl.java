package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.springwind.entity.ShopUser;
import com.baomidou.springwind.mapper.ShopOrderMapper;
import com.baomidou.springwind.mapper.ShopUserFilterRecordMapper;
import com.baomidou.springwind.mapper.ShopUserMapper;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.redis.MapUtil;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.redis.ranval;
import com.baomidou.springwind.service.IShopUserFilterRecordService;
import com.baomidou.springwind.service.IShopUserService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商城用户表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopUserServiceImpl extends BaseServiceImpl<ShopUserMapper, ShopUser> implements IShopUserService {
    @Autowired
    private ShopUserMapper shopUserMapper;

    @Autowired
    private IShopUserFilterRecordService shopUserFilterRecordService;

    @Autowired
    private ShopUserFilterRecordMapper shopUserFilterRecordMapper;
    @Autowired
    private ShopOrderMapper shopOrderMapper;
    @Override
    public String getUserInfo(String user_id) {
        JSONObject param= new JSONObject();
        try{
            param.put("result","0");
            JSONObject content= new JSONObject();
            ShopUser su= shopUserMapper.selectById(user_id);
            content.put("id",su.getId());
            content.put("nickname", SerializeAPI.toUtf8(su.getNickname()));
            content.put("head_image_addr",su.getHeadImageAddr());
            content.put("mobile_phone",su.getMobilePhone());
            content.put("date_of_birth", DateUtils.Date2String(su.getDateOfBirth()));
            content.put("skin_type",su.getSkinType());
            content.put("email",su.getEmail());
            content.put("default_addr",su.getDefaultAddr());
            content.put("shard_count",su.getShardCount());
            content.put("evaluate_count",su.getEvaluateCount());
            content.put("shard_bonus",su.getShardBonus());
            content.put("evaluate_bonus",su.getEvaluateBonus());
            content.put("sex", su.getSex());
            param.put("content",content);
            return JSON.toJSONString(param);
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            param.put("content",SerializeAPI.toUtf8("用户信息不存在"));
            return JSON.toJSONString(param);
        }
    }
    @Override
    public String changeUserInfo(String user_id, int info_type, String new_value) {
        JSONObject param= new JSONObject();
        try{
            JSONObject content= new JSONObject();
            param.put("result","0");
            //ShopUser su= shopUserMapper.selectById(user_id);
            ShopUser su=new ShopUser();
            if (info_type==0){
                su.setId(Long.parseLong(user_id));
                su.setNickname(new_value);
                shopUserMapper.updateById(su);
            }else if (info_type==1) {
                su.setId(Long.parseLong(user_id));
                su.setSkinType(Integer.parseInt(new_value));
                shopUserMapper.updateById(su);
            }else if(info_type==2){
                su.setId(Long.parseLong(user_id));
                if (Integer.parseInt(new_value)==0){
                    su.setSex(0);
                    shopUserMapper.updateById(su);
                }else {
                    su.setId(Long.parseLong(user_id));
                    su.setSex(1);
                    shopUserMapper.updateById(su);
                }
            }else if(info_type==3){
                   su.setId(Long.parseLong(user_id));
                   su.setDateOfBirth(DateUtils.String2Date(new_value));
                   shopUserMapper.updateById(su);
            }else {
                su.setId(Long.parseLong(user_id));
                su.setHeadImageAddr(new_value);
                shopUserMapper.updateById(su);
            }
            param.put("content",SerializeAPI.toUtf8("修改成功"));
            return JSON.toJSONString(param);
        }catch (Exception e){
            e.printStackTrace();
            param.put("result","1");
            param.put("content",SerializeAPI.toUtf8("修改失败"));
            return JSON.toJSONString(param);
        }
    }

    @Override
    public List<ShopUser> selectMultiShopUserList(Map<String, Object> map) {
        return baseMapper.selectMultiShopUserList(map);
    }

    @Override
    public Integer selectMultiShopUserCount(Map<String, Object> map) {
        return baseMapper.selectMultiShopUserCount(map);
    }
/*根据条件生成500虚拟账户*/
    @Override
    public  void setPitchUser() {
        ShopUser su=new ShopUser();
        su.setHeadImageAddr("http://img.ciaotalking.com/shopimg/20170426/189e7d12-8dda-498b-8914-f75b1a839fda.jpeg");
        for(int i=0;i<500;i++){
           Map<String,String> name= ranval.getChineseName();
            su.setNickname(name.get("name"));
            su.setSex(Integer.parseInt(name.get("sex")));
            su.setDefaultAddr(-1);
            su.setMobilePhone(ranval.getTel());
            su.setEmail(ranval.getEmail(6, 10));
            // 点评数量在0-100之间随机
           // 分享数量在100-200之间随机
            int val = (int)(Math.random()*100+1);
            int val1 = (int)(Math.random()*100+1)+100;
            su.setDateOfBirth(DateUtils.randomDate("1985-01-01","1997-12-31"));
            su.setEvaluateCount(val);
            int[] str={0,1,2,3,4};
            int j=(int)(str.length*Math.random());
            su.setSkinType(j);
            su.setOpenId("o6Ot3wqSbKwWavgAlPBLzEJaf2"+String.valueOf(i));
            su.setShardCount(val1);
            su.setShardBonus(val+i);
            su.setEvaluateBonus(val1+i);
            su.setState(0);
            shopUserMapper.insert(su);
        }
    }/**/

    /**
     * 10查询所有的用户ID
     *
     * @return
     */
    @Override
    public String selectUserIdAll() {
        String[] userIdAll = baseMapper.selectUserIdAll();
        return JSONArray.toJSONString(userIdAll);
    }

    /**
     * 2根据用户的订单数量
     *
     * @param x
     * @return
     */
    @Override
    public String selectUserIdByOrderCount( Integer x,Integer y) {
        String[] userIds = baseMapper.selectUserIdByOrderCount(x);
        List<String> ids= MapUtil.getRandom(userIds, y);
        return JSONArray.toJSONString(ids);
    }

    /**
     * 3根据用户的点评数量
     *
     * @param x
     * @return
     */
    @Override
    public String selectUserIdByEvaluateCount( Integer x,Integer y) {
        String[] userIds = baseMapper.selectUserIdByEvaluateCount(x);
        List<String> ids = MapUtil.getRandom(userIds,y);
        return JSONArray.toJSONString(ids);
    }

    /**
     * 4根据用户的分享数量
     *
     * @param x
     * @return
     */
    @Override
    public String selectUserIdBySharedCount( Integer x,Integer y) {
        String[] userIds = baseMapper.selectUserIdBySharedCount(x);
        List<String> ids = MapUtil.getRandom(userIds,y);
        return JSONArray.toJSONString(ids);
    }

    /**
     * 5根据用户的消费金额
     *
     * @param x
     * @return
     */
    @Override
    public String selectUserIdByOrderTotal(Integer x,Integer y) {
        String[] userIds = baseMapper.selectUserIdByOrderTotal(x);
        List<String> ids = MapUtil.getRandom(userIds,y);
        return JSONArray.toJSONString(ids);
    }

    /**
     * 6根据用户的订单数量排名
     *
     * @param x
     * @return
     */
    @Override
    public String selectUserIdByOrderCountRank( Integer x) {
        String[] userIds = baseMapper.selectUserIdByOrderCountRank(x);
        return JSONArray.toJSONString(userIds);
    }

    /**
     * 7根据用户的点评数量排名
     *
     * @param x
     * @return
     */
    @Override
    public String selectUserIdByEvaluateCountRank( Integer x) {
        String[] userIds = baseMapper.selectUserIdByEvaluateCountRank(x);
        return JSONArray.toJSONString(userIds);
    }

    /**
     * 8根据用户的分享数量排名
     *
     * @param x
     * @return
     */
    @Override
    public String selectUserIdBySharedCountRank( Integer x) {
        String[] userIds = baseMapper.selectUserIdBySharedCountRank(x);
        return JSONArray.toJSONString(userIds);
    }

    /**
     * 9根据用户的消费金额排名
     *
     * @param x
     * @return
     */
    @Override
    public String selectUserIdByOrderTotalRank( Integer x) {
        String[] userIds = baseMapper.selectUserIdByOrderTotalRank(x);
        return JSONArray.toJSONString(userIds);
    }

}
