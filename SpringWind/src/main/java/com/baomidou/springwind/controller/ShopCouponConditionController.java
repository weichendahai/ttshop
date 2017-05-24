package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.ShopCoupon;
import com.baomidou.springwind.entity.ShopCouponCondition;
import com.baomidou.springwind.entity.po.PoShopCouponCondition;
import com.baomidou.springwind.service.IShopCouponConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券使用条件表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/operate/coupon/condition")
public class ShopCouponConditionController extends BaseController {

    @Autowired
    private IShopCouponConditionService shopCouponConditionService;

    @ResponseBody
    @Permission("8005")
    @RequestMapping("/getShopCouponConditionList")
    public String getShopCouponConditionList(String searchKey){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        if(searchKey!=null){
            map.put("searchKey",searchKey.trim());
        }
        List<PoShopCouponCondition> list = shopCouponConditionService.selectMultiShopCouponList(map);
        Integer couponCount = shopCouponConditionService.selectMultiShopCouponCount(map);
        return multiToJson(couponCount,list);
    }

    @ResponseBody
    @Permission("8005")
    @RequestMapping("/addShopCouponCondition")
    public String addShopCouponCondition(PoShopCouponCondition pocc){
        boolean add = false;
        if(pocc!=null){
            add = shopCouponConditionService.addPoShopCouponCondition(pocc);
        }
        return callbackSuccess(true);
    }
}
