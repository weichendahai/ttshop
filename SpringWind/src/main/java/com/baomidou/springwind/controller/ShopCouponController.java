package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.ShopCoupon;
import com.baomidou.springwind.entity.ShopGiftCard;
import com.baomidou.springwind.service.IShopCouponConditionService;
import com.baomidou.springwind.service.IShopCouponService;
import com.baomidou.springwind.service.IShopGiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 优惠券表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/operate/coupon")
public class ShopCouponController extends BaseController{

    @Autowired
    private IShopCouponService shopCouponService;

    @Autowired
    private IShopCouponConditionService shopCouponConditionService;

    @Permission("8005")
    @RequestMapping("/{html}")
    public String list(Model model,@PathVariable("html") String html) {
        if (html != null && html != "") {
            if(html.contains("list")){
                return "/coupon/list";
            }
            if(html.contains("edit")){
                return "/coupon/edit";
            }
        }
        return "/error/404";
    }

    /*@ResponseBody
    @Permission("8005")
    @RequestMapping("/getShopCouponList")
    public String getShopCouponList(){
    *//*    Page<ShopGiftCard> page = getPage();
    return toJson(shopGiftCardService.selectPage(page,null));*//*
        List<ShopCoupon> list = shopCouponService.selectList(null);
        JSONObject jo = new JSONObject();
        jo.put("total",list.size());
        jo.put("rows",list);
        return toJson(jo) ;
    }*/

    /*@ResponseBody
    @Permission("8005")
    @RequestMapping("/addShopCoupon")
    public String addShopCoupon(ShopCoupon shopCoupon){
        boolean add =false;
        if(shopCoupon!=null){
            add = shopCouponService.insert(shopCoupon);
        }
        return callbackSuccess(add);
    }*/
}
