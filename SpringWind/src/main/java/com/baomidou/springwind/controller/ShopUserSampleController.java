package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.ShopSample;
import com.baomidou.springwind.service.IShopUserFilterRecordService;
import com.baomidou.springwind.service.IShopUserSampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户试用订单表， 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/operate/usersample")
public class ShopUserSampleController extends BaseController{

    @Autowired
    private IShopUserSampleService shopUserSampleService;

    @Autowired
    private IShopUserFilterRecordService shopUserFilterRecordService;
    //根据条件筛选用户
    @ResponseBody
    @Permission("8010")
    @RequestMapping("/filter")
    public String filterUserSample(Integer scheme,Integer x,Integer y){
        String ids = null;
        if(scheme!=null){
            if(scheme==1){
                ids = shopUserSampleService.autoSample(x);
            }
            if(scheme==2){
                ids = shopUserSampleService.SampleByOrderCount(x, y);
            }
            if(scheme==3){
                ids = shopUserSampleService.SampleByEvateCount(x, y);
            }
            if(scheme==4){
                ids = shopUserSampleService.SampleBySharedCount(x, y);
            }
            if(scheme==5){
                ids = shopUserSampleService.SampleByMoneyCount(x, y);
            }
            if(scheme==6){
                ids = shopUserSampleService.SampleByMoneyTotal(x);
            }
            if(scheme==7){
                ids = shopUserSampleService.SampleByEvalteTotal(x);
            }
            if(scheme==8){
                ids = shopUserSampleService.SampleBySharedTotal(x);
            }
            if(scheme==9){
                ids = shopUserSampleService.SampleByMoneyTotal(x);
            }
        }
        return ids;
    }

    @ResponseBody
    @Permission("8010")
    @RequestMapping("/handler")
    public String handlerUserSample(Integer scheme,String userSampleIds){
        boolean add = false;
        boolean add1 = false;
        boolean add2 = false;
        if(scheme!=null&&userSampleIds!=null){
            List<Integer> list = JSONArray.parseArray(userSampleIds, Integer.class);
            List<Integer> userIds = shopUserSampleService.selectUserIdsByUserSampleIds(list);
            add = shopUserFilterRecordService.addShopUserFilterRecordByUserIds(userIds,0, scheme);
            add1 = shopUserSampleService.updateShopUserSampleBatchByIds(list);
            add2 = shopUserSampleService.autoAddOrdersByIds(list);
        }
        return Boolean.toString(true);
    }
}
