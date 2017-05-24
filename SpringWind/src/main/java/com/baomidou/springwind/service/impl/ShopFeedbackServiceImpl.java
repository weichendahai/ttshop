package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.ShopFeedback;
import com.baomidou.springwind.entity.po.PoShopFeedback;
import com.baomidou.springwind.mapper.ShopFeedbackMapper;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.service.IShopFeedbackService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Woody
 * @since 2017-05-05
 */
@Service
public class ShopFeedbackServiceImpl extends BaseServiceImpl<ShopFeedbackMapper, ShopFeedback> implements IShopFeedbackService {
    @Autowired
    private ShopFeedbackMapper shopFeedbackMapper;
  //获取反馈列表
    @Override
    public String getFeedBack(String user_id, String page_no){
        JSONObject content=new JSONObject();
        JSONObject param=new JSONObject();
        JSONObject backs=new JSONObject();
        try{
        param.put("result",0);
        ShopFeedback sfb=new ShopFeedback();
        sfb.setFrom(Integer.parseInt(user_id));
        sfb.setFid(0);
        int i=shopFeedbackMapper.selectCount(new EntityWrapper<ShopFeedback>(sfb));
        int maxPage=i/10;
        if (Integer.parseInt(page_no)+1>maxPage){
            param.put("next_page_no", -1);
        }else {
            param.put("next_page_no", Integer.parseInt(page_no) + 1);
        }
       List<ShopFeedback> shopFeedbacks=shopFeedbackMapper.selectByUserId(user_id, Integer.parseInt(page_no) * 10);
        JSONArray jb=new JSONArray();
        for (ShopFeedback shopFeedback:shopFeedbacks){
            content.put("id",shopFeedback.getId());
            content.put("fid",shopFeedback.getFid());
            content.put("create_date",DateUtils.Date2String(shopFeedback.getCreateDate()));
            if (shopFeedback.getReadDate()==null){
                content.put("read_date","");
            }else {
                content.put("read_date", shopFeedback.getReadDate());
            }
            content.put("read_date",shopFeedback.getReadDate());
            content.put("from",shopFeedback.getFrom());
            content.put("content",SerializeAPI.toUtf8(shopFeedback.getContent()));
            content.put("img_addr",shopFeedback.getImgAddr());
            ShopFeedback shopFeedback1=new ShopFeedback();
            shopFeedback1.setFid(shopFeedback.getId());
           List<ShopFeedback> backLists= shopFeedbackMapper.selectList(new EntityWrapper<ShopFeedback>(shopFeedback1));
            JSONArray ja=new JSONArray();
            for (ShopFeedback backList:backLists){
                backs.put("id",backList.getId());
                backs.put("fid",backList.getFid());
                backs.put("create_date", backList.getCreateDate());
                if (backList.getReadDate()==null){
                    backs.put("read_date","");
                }else {
                backs.put("read_date", backList.getReadDate());
                }
                backs.put("from",backList.getFrom());
                backs.put("content", SerializeAPI.toUtf8(backList.getContent()));
                backs.put("img_addr",backList.getImgAddr());
                String str1= JSON.toJSONString(backs, SerializerFeature.DisableCircularReferenceDetect);
                ja.add(JSON.parseObject(str1));
            }
            content.put("backs",ja);
            String str2= JSON.toJSONString(content, SerializerFeature.DisableCircularReferenceDetect);
            jb.add(JSON.parseObject(str2));
        }
        param.put("content",jb);
        return JSON.toJSONString(param);
        }catch ( Exception e){
            e.printStackTrace();
            param.put("result",1);
           return JSON.toJSONString(param);
        }
    }
//提交反馈意见
    @Override
    public String submitFeedBack(String user_id, String fid, String img_addr, String content){
        JSONObject param=new JSONObject();
        param.put("result",0);
        param.put("content","");
       ShopFeedback shopFeedback=new ShopFeedback();
        shopFeedback.setFid(Integer.parseInt(fid));
        shopFeedback.setFrom(Integer.parseInt(user_id));
        shopFeedback.setImgAddr(img_addr);
        shopFeedback.setContent(content);
        shopFeedback.setCreateDate(new Date());
        shopFeedbackMapper.insert(shopFeedback);
        return JSON.toJSONString(param);
    }
//修改读取状态
    @Override
    public String readFeedBack(String user_id, String id) {
        JSONObject param=new JSONObject();
        ShopFeedback shopFeedback=new ShopFeedback();
        shopFeedback.setFid(Integer.parseInt(id));
        shopFeedback.setReadDate(null);
        shopFeedback.setFrom(-1);
       List<ShopFeedback> lists=shopFeedbackMapper.selectList(new EntityWrapper<ShopFeedback>(shopFeedback));
        for (ShopFeedback list:lists){
            shopFeedback.setId(list.getId());
            shopFeedback.setReadDate(new Date());
            shopFeedbackMapper.updateById(shopFeedback);
        }
        param.put("result", 0);
        param.put("content","");
        return JSON.toJSONString(param);
    }

    /**
     * 查询用户的反馈主题列表
     * @param map
     * @return
     */
    @Override
    public List<PoShopFeedback> selectShopFeedbackThemeList(Map<String, Object> map) {
        List<PoShopFeedback> feedbacks = baseMapper.selectShopFeedbackThemeList(map);
        for(PoShopFeedback posf:feedbacks){
            if(null!=posf.getReadDate()){
                PoShopFeedback feedback = baseMapper.selectRecentNotReadFeedbackById(posf.getId());
                if(feedback!=null){
                    if(feedback.getReadDate()==null){
                        posf.setReadDate(null);
                    }
                }
            }
        }
        return feedbacks;
    }

    /**
     * 查询用户的反馈主题列表数量
     * @param map
     * @return
     */
    @Override
    public Integer selectShopFeedbackThemeCount(Map<String, Object> map) {
        return baseMapper.selectShopFeedbackThemeCount(map);
    }

    /**
     * 根据主题的ID显示该主题下的信息列表
     * @param feedbackId
     * @return
     */
    @Override
    public List<PoShopFeedback> selectShopFeedbackMessageList(Integer feedbackId) {
        List<PoShopFeedback> feedbacks = baseMapper.selectShopFeedbackMessageList(feedbackId);
        return feedbacks;
    }

    /**
     * 通过fid更改未读状态
     * @param feedbackId
     * @return
     */
    @Override
    public boolean updateShopFeedbackByFid(Integer feedbackId) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("fid", feedbackId);
        List<ShopFeedback> feedbacks = selectByMap(map);
        int count = 0;
        int c =0;
        for(ShopFeedback sf:feedbacks){
            if(sf.getFrom()>=1){
                sf.setReadDate(new Date());
            }
            count= baseMapper.updateById(sf);
            c+=count;
        }
        map.remove("fid");
        map.put("id",feedbackId);
        List<ShopFeedback> themes = selectByMap(map);
        for(ShopFeedback s:themes){
            if(s.getReadDate()==null){
                s.setReadDate(new Date());
            }
           count= baseMapper.updateById(s);
            c+=count;
        }
        if(c==(feedbacks.size()+themes.size())){
            return true;
        }
        return false;
    }

    /**
     * 添加客服的回复消息
     * @param shopFeedback
     * @return
     */
    @Override
    public boolean insertShopFeedback(ShopFeedback shopFeedback) {
        shopFeedback.setFrom(-1);
        shopFeedback.setCreateDate(new Date());
        boolean add = insert(shopFeedback);
        return add;
    }
}
