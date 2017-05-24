package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopUserFilterScheme;
import com.baomidou.springwind.entity.ShopUserPromo;
import com.baomidou.springwind.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户拥有的优惠券红包表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/operate/promo")
public class ShopUserPromoController extends BaseController {

    @Autowired
    private IShopCouponService shopCouponService;

    @Autowired
    private IShopUserPromoService shopUserPromoService;

    @Autowired
    private IShopUserService shopUserService;

    @Autowired
    private IShopUserFilterRecordService shopUserFilterRecordService;

    @Autowired
    private IShopUserFilterSchemeService shopUserFilterSchemeService;

    @Permission("8006")
    @RequestMapping("/{html}")
    public String list(Model model,@PathVariable("html") String html) {
        if (html != null && html != "") {
            if(html.equals("list")){
                return "/award/list";
            }
            if(html.equals("add")){
                return "/award/add";
            }
        }
        return "/error/404";
    }

    @ResponseBody
    @Permission("8006")
    @RequestMapping("/getShopUserPromoList")
    public String selectMultiShopUserPromo(Integer _index,Integer _size){
        List<ShopUserPromo> records = shopUserPromoService.selectMulitShopUserPromoList(_index,_size);
        Integer total = shopUserPromoService.selectMulitShopUserPromoListTotal();
        if(records!=null&&records.size()>0&&total!=null){
            JSONObject jo = new JSONObject();
        jo.put("total",total);
        jo.put("rows",records);
        return toJson(jo);
        }
        return "['':'']";
    }

    @Permission("8005")
    @RequestMapping("/coupon/allot")
    public String filter(Model model){
        List<ShopUserFilterScheme> scs = shopUserFilterSchemeService.selectAll();
        model.addAttribute("schemes",scs);
        model.addAttribute("defaultDesc",scs.get(0).getDescription());
        return "/coupon/allot";
    }

    @ResponseBody
    @Permission("8005")
    @RequestMapping("/coupon/filter")
    public String filterUser(Integer scheme,Integer x,Integer y){
        String ids = null;
        if(scheme!=null){
            if(scheme==10){
                ids = shopUserService.selectUserIdAll();
            }
            if(scheme==2){
                ids = shopUserService.selectUserIdByOrderCount(x, y);
            }
            if(scheme==3){
                ids = shopUserService.selectUserIdByEvaluateCount(x, y);
            }
            if(scheme==4){
                ids = shopUserService.selectUserIdBySharedCount(x, y);
            }
            if(scheme==5){
                ids = shopUserService.selectUserIdByOrderTotal(x, y);
            }
            if(scheme==6){
                ids = shopUserService.selectUserIdByOrderCountRank(x);
            }
            if(scheme==7){
                ids = shopUserService.selectUserIdByEvaluateCountRank(x);
            }
            if(scheme==8){
                ids = shopUserService.selectUserIdBySharedCountRank(x);
            }
            if(scheme==9){
                ids = shopUserService.selectUserIdByOrderTotalRank(x);
            }
        }
        return ids;
    }

    @ResponseBody
    @Permission("8005")
    @RequestMapping("/coupon/handler")
    public String handlerUser(Integer scheme,String userIds,Integer promoId,String endDate){
        boolean add = false;
        boolean add1 = false;
        if(scheme!=null&&userIds!=null){
            List<Integer> list = JSONArray.parseArray(userIds, Integer.class);
           add = shopUserFilterRecordService.addShopUserFilterRecordByUserIds(list, 1, scheme);
            ShopUserPromo shopUserPromo = new ShopUserPromo();
            shopUserPromo.setPromoId(promoId);
            Date end = null;
            try {
                end = (new SimpleDateFormat("yyyy-MM-dd")).parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            shopUserPromo.setEndDate(end);
            add1 = shopUserPromoService.createCouponBatch(list,shopUserPromo);
        }
        return Boolean.toString(add&&add1);
    }
}
