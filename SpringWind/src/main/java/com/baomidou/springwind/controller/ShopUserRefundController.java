package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.entity.po.PoShopUserRefund;
import com.baomidou.springwind.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户退款记录表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
@Controller
@RequestMapping("/operate/refund")
public class ShopUserRefundController extends BaseController {

    @Autowired
    private IShopUserRefundService shopUserRefundService;

    @Autowired
    private IShopUserService shopUserService;

    @Autowired
    private IShopItemService shopItemService;

    @Autowired
    private IShopOrderService shopOrderService;

    @Autowired
    private IShopOrderItemService shopOrderItemService;

    @Permission("8002")
    @RequestMapping("/list")
    public String list(){
        return "/refund/list";
    }

    @Permission("8002")
    @RequestMapping("/detail")
    public String edit(Model model,Integer refundId){
        /*Map<String,Object> map = new HashMap<String, Object>();
        if(refundId!=null){
            map.put("refundId",refundId);
        }*/
        PoShopUserRefund refund = shopUserRefundService.selectShopUserRefundDetailById(refundId);
        if(refund!=null){
            model.addAttribute("refund",refund);
        }
        return "/refund/refunddetail";
    }

    @ResponseBody
    @Permission("8002")
    @RequestMapping("/editShopUserRefund")
    public String edit(){
        return "/return/list";
    }

    @ResponseBody
    @Permission("8002")
    @RequestMapping("/update")
    public String update(ShopUserRefund shopUserRefund){
        boolean ud = false;
        if(shopUserRefund!=null){
          ud =   shopUserRefundService.updateById(shopUserRefund);
        }
        return callbackSuccess(ud);
    }

    //查询退款列表
    @ResponseBody
    @Permission("8002")
    @RequestMapping("/getShopUserRefundList")
    public String selectPoUserRefundList(String searchKey){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        if(searchKey!=null){
            searchKey=searchKey.trim();
            if(searchKey.matches("[0-9]+")){
                map.put("orderCode",searchKey);
            }else {
                map.put("searchKey",searchKey);
            }
        }
        List<PoShopUserRefund> shopUserRefunds = shopUserRefundService.selectMultiShopUserRefundList(map);
        Integer total = shopUserRefundService.selectMultiShopUserRefundCount(map);
        return multiToJson(total,shopUserRefunds);
    }
}
