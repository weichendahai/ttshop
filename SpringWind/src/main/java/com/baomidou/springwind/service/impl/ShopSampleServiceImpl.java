package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopSample;
import com.baomidou.springwind.entity.ShopUserSample;
import com.baomidou.springwind.mapper.ShopItemMapper;
import com.baomidou.springwind.mapper.ShopSampleMapper;
import com.baomidou.springwind.mapper.ShopUserSampleMapper;
import com.baomidou.springwind.redis.MapUtil;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.service.IShopSampleService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试用表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopSampleServiceImpl extends BaseServiceImpl<ShopSampleMapper, ShopSample> implements IShopSampleService {
    @Autowired
    private ShopSampleMapper shopSampleMapper;
    @Autowired
    private ShopUserSampleMapper shopUserSampleMapper;
    @Autowired
    private ShopItemMapper shopItemMapper;

    /*
    * 试用列表的展示
    * */
    @Override
    public String getSampleList(String user_id, String page_no, String list_type) {
        JSONObject param = new JSONObject();
        JSONObject content = new JSONObject();
        JSONObject sample = new JSONObject();
        JSONArray samples = new JSONArray();
        try {
            param.put("result", "0");

            if (Integer.parseInt(list_type.trim()) == 1) {
                //展示自己的信息
                ShopUserSample sum=new ShopUserSample();
                sum.setUserId(Integer.parseInt(user_id));
                int i=shopUserSampleMapper.selectCount(new EntityWrapper<ShopUserSample>());
                int maxPage=i/10;
                if (Integer.parseInt(page_no)+1>maxPage){
                    content.put("next_page_no",-1);
                    param.put("content",content);
                }else {
                    content.put("next_page_no",Integer.parseInt(page_no)+1);
                }
                List<ShopUserSample> userlist = shopUserSampleMapper.selectByUserid(user_id,Integer.parseInt(page_no)*10);
                for (ShopUserSample saObj : userlist) {
                    ShopSample ss = shopSampleMapper.selectById(saObj.getSampleId());
                    ShopItem item = shopItemMapper.selectById(ss.getItemId());
                    sample.put("item_id", ss.getItemId());
                    sample.put("item_name", SerializeAPI.toUtf8(item.getItemName()));
                    sample.put("sample_image_addr", ss.getSampleImageAddr());
                    sample.put("apply_count",ss.getApplyCount());
                    sample.put("item_count", ss.getItemCount());
                    sample.put("end_date", ss.getEndDate());
                    sample.put("state", saObj.getState());
                    String str = JSON.toJSONString(sample, SerializerFeature.DisableCircularReferenceDetect);
                    samples.add(JSON.parseObject(str));
                }
                content.put("samples", samples);
                param.put("content", content);

            } else if (Integer.parseInt(list_type.trim()) == 0) {
                //展示全部的信息
                ShopUserSample sum=new ShopUserSample();
                sum.setUserId(Integer.parseInt(user_id));
                int i = shopSampleMapper.selectAllCount(user_id);
                //int i=shopUserSampleMapper.selectCount(new EntityWrapper<ShopUserSample>());
                int maxPage=i/10;
                if (Integer.parseInt(page_no)+1>maxPage){
                    content.put("next_page_no",-1);
                    param.put("content",content);
                }else {
                    content.put("next_page_no",Integer.parseInt(page_no)+1);
                }
                List<ShopSample> userAll = shopSampleMapper.selectAll(user_id,Integer.parseInt(page_no)*10);
                for (ShopSample ssp : userAll) {
                    ShopItem item = shopItemMapper.selectById(ssp.getItemId());
                    sample.put("item_id", ssp.getItemId());
                    sample.put("item_name",SerializeAPI.toUtf8(item.getItemName()));
                    sample.put("sample_image_addr", ssp.getSampleImageAddr());
                    sample.put("sample_id", ssp.getId());
                    sample.put("item_count", ssp.getItemCount());
                    sample.put("apply_count",ssp.getApplyCount());
                    sample.put("end_date", ssp.getEndDate());
                    //sample.put("state",);
                    String str = JSON.toJSONString(sample, SerializerFeature.DisableCircularReferenceDetect);
                    samples.add(JSON.parseObject(str));
                }
                content.put("samples", samples);
                param.put("content", content);
            }
            return JSON.toJSONString(param);
        } catch (Exception e) {
            e.printStackTrace();
            param.put("result", "1");
            return JSON.toJSONString(param);
        }
    }

    @Override
    public String applySample(String user_id, String sample_id) {
        JSONObject param=new JSONObject();
        //修改申请试用状态
        try {
            param.put("result","0");
            //String state="1";
            ShopUserSample sum=new ShopUserSample();
            sum.setState(0);
            sum.setUserId(Integer.parseInt(user_id));
            sum.setSampleId(Integer.parseInt(sample_id));
            shopUserSampleMapper.insert(sum);
            //shopUserSampleMapper.applySample(user_id,sample_id,state);
            param.put("content", "");
            return JSON.toJSONString(param);
        }catch (Exception e){
            e.printStackTrace();
            param.put("result", "1");
            return JSON.toJSONString(param);
        }
    }

    @Override
    public List<ShopSample> selectMultiShopSampleList(Map<String, Object> map) {
        return baseMapper.selectMultiShopSampleList(map);
    }

    @Override
    public Integer selectMultiShopSampleCount(Map<String, Object> map) {
        return baseMapper.selectMultiShopSampleCount(map);
    }

    @Override
    public Integer insertShopSample(ShopSample shopSample) {
        shopSample.setCreateDate(new Date());
        return baseMapper.insert(shopSample);
    }

    @Override
    public List<ShopSample> selectItemNameList(Map<String, Object> map) {
        return baseMapper.selectItemNameList(map);
    }

    @Override
    public List<ShopSample> selectMultiShopSampleItemNameList(Map<String, Object> map) {
        return baseMapper.selectMultiShopSampleItemNameList(map);
    }

    @Override
    public Integer selectMultiShopSampleItemNameCount(Map<String, Object> map) {
        return baseMapper.selectMultiShopSampleItemNameCount(map);
    }

}
