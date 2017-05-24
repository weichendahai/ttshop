package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.po.PoShopBonusPayment;
import com.baomidou.springwind.service.IShopBonusPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 佣金提现表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
@Controller
@RequestMapping("/bonus")
public class ShopBonusPaymentController extends BaseController{

    @Autowired
    private IShopBonusPaymentService shopBonusPaymentService;


    @RequestMapping("/list")
    @Permission("11001")
    public String getList(Model model){
        return "/bonus/list";
    }

    @ResponseBody
    @RequestMapping("/getBonusPaymentList")
    @Permission("11001")
    public String getBonusPaymentList(String searchKey){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        if(searchKey!=null){
            map.put("searchKey",searchKey);
        }
        List<PoShopBonusPayment> payments = shopBonusPaymentService.selectMultiBonusPaymentList(map);
        Integer total = shopBonusPaymentService.selectMultiBonusPaymentCount(map);
        return multiToJson(total,payments);
    }

    @ResponseBody
    @RequestMapping("/extract")
    @Permission("11001")
    public String extractBonus(Integer bonusId){
        boolean update=false;
        if(bonusId!=null){
            update = shopBonusPaymentService.updateShopBonusPaymentState(bonusId);
        }
        return Boolean.toString(update);
    }

}
