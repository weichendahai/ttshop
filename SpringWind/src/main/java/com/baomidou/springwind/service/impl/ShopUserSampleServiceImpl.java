package com.baomidou.springwind.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.springwind.entity.ShopUserSample;
import com.baomidou.springwind.mapper.ShopUserSampleMapper;
import com.baomidou.springwind.redis.MapUtil;
import com.baomidou.springwind.service.IShopUserSampleService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 用户试用订单表， 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopUserSampleServiceImpl extends BaseServiceImpl<ShopUserSampleMapper, ShopUserSample> implements IShopUserSampleService {

    //全随机：从用户状态正常的用户中选出所有的
    @Override
    public String autoSample(int x) {
        //将所有满足条件的用户id放入数组
        String[] sampleIds=baseMapper.getSampleIds();
        List<String > lists= MapUtil.getRandom(sampleIds,x);
        /*JSONUtils.toJSONString(lists);*/
        return JSONUtils.toJSONString(lists);

    }
    //根据订单数量订单数量大于x的y个用户筛选
    @Override
    public String SampleByOrderCount(int x, int y) {
        //查询用户信息按照订单数量排序
        String[] sampleIds=baseMapper.SampleByOrderCount(x);
        List<String >lists= MapUtil.getRandom(sampleIds,y);
        return JSONArray.toJSONString(lists);
    }
    //根据点评数量大于x的y个用户筛选
    @Override
    public String SampleByEvateCount(int x, int y) {
        String[] sampleIds=baseMapper.SampleByEvateCount(x);
        List<String >lists= MapUtil.getRandom(sampleIds,y);
        return JSONArray.toJSONString(lists);
    }
    //根据分享数量大于x的y个用户筛选
    @Override
    public String SampleBySharedCount(int x, int y) {
        String[] sampleIds=baseMapper.SampleBySharedCount(x);
        List<String >lists= MapUtil.getRandom(sampleIds,y);
        return JSONArray.toJSONString(lists);
    }
    //按照消费总额
    @Override
    public String SampleByMoneyCount(int x, int y) {
        String[] sampleIds=baseMapper.SampleByMoneyCount(x);
        List<String >lists= MapUtil.getRandom(sampleIds,y);
        return JSONArray.toJSONString(lists);
    }
    //按照订单总量
    @Override
    public String SampleByOrderTotal(int x) {
        String[] sampleIds=baseMapper.SampleByOrderTotal(x);
        return JSONArray.toJSONString(Arrays.asList(sampleIds));
    }
    //按照点评总量前x个
    @Override
    public String SampleByEvalteTotal(int x) {
        String[] sampleIds=baseMapper.SampleByEvalteTotal(x);
        return JSONArray.toJSONString(Arrays.asList(sampleIds));
    }
    //按照分享的前x个
    @Override
    public String SampleBySharedTotal(int x) {
        String[] sampleIds=baseMapper.SampleBySharedTotal(x);
        return JSONArray.toJSONString(Arrays.asList(sampleIds));
    }

    @Override
    public String SampleByMoneyTotal(int x) {
        String[] sampleIds=baseMapper.SampleByMoneyTotal(x);
        return JSONArray.toJSONString(Arrays.asList(sampleIds));
    }

    @Override
    public boolean autoAddOrdersByIds(List<Integer> shopUserSampleIds) {
        for(Integer s:shopUserSampleIds){

        }
        return true;
    }

    /**
     * 通过userSampleIds查询用户的Id
     * @param userSampleIds
     * @return
     */
    @Override
    public List<Integer> selectUserIdsByUserSampleIds(List<Integer> userSampleIds) {
        List<ShopUserSample> samples = baseMapper.selectBatchIds(userSampleIds);
        List<Integer> userIds = new ArrayList<Integer>();
        for(ShopUserSample sus:samples){
            userIds.add(sus.getUserId());
        }
        return userIds;
    }

    /**
     * 通过usersampleIds更改申请用户的状态
     * @param userSampleIds
     * @return
     */
    @Override
    public boolean updateShopUserSampleBatchByIds(List<Integer> userSampleIds) {
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("state",0);
        List<ShopUserSample> userSamples = selectByMap(map);
        Integer count =0;
        //更改申请试用成功的状态
        for(ShopUserSample sus:userSamples){
            for(Integer s:userSampleIds){
                sus.setState(2);//未中签
                if(sus.getId()==s){
                    sus.setState(1);//中签
                    break;
                }
            }
           count  = baseMapper.updateById(sus);
            count++;
        }
        if(userSamples.size()==count){
            return true;
        }
        return false;
    }
}
