package com.baomidou.springwind.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopOrder;
import com.baomidou.springwind.entity.ShopOrderItemProperties;
import com.baomidou.springwind.entity.ShopUser;
import com.baomidou.springwind.entity.ShopUserAddr;
import com.baomidou.springwind.entity.po.PoShopOrderItemProperties;
import com.baomidou.springwind.service.IShopOrderItemPropertiesService;
import com.baomidou.springwind.service.IShopOrderService;
import com.baomidou.springwind.service.IShopUserAddrService;
import com.baomidou.springwind.service.IShopUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单商品关系表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-31
 */
@Controller
@RequestMapping("/order/orderitemproperties")
public class ShopOrderItemPropertiesController extends BaseController {

    @Autowired
    private IShopOrderService shopOrderService;

    @Autowired
    private IShopUserAddrService shopUserAddrService;

    @Autowired
    private IShopUserService shopUserService;

    @Autowired
    private IShopOrderItemPropertiesService shopOrderItemPropertiesService;

    @Permission("7001")
    @RequestMapping("/orderitem")
    public String list(Model model,Long orderId){
        if(orderId!=null){
            ShopOrder order = shopOrderService.selectById(orderId);
            if(order!=null){
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateDate());
                model.addAttribute("orderCreateDate",date);
                double d = order.getOrderTotal()/100.00;
                double freight = order.getFreight()/100.00;
                model.addAttribute("orderTotal",Double.toString(d));
                model.addAttribute("freight",Double.toString(freight));
                model.addAttribute("shopOrder",order);
            }
            ShopUser shopUser = shopUserService.selectById(order.getUserId());
            if(shopUser!=null){
                model.addAttribute("shopUser",shopUser);
            }
            ShopUserAddr shopUserAddr = shopUserAddrService.selectById(shopUser.getDefaultAddr());
            if(shopUserAddr!=null){
                model.addAttribute("shopUserAddr",shopUserAddr);
            }
        }
        return "/order/orderdetail";
    }


    @ResponseBody
    @Permission("7001")
    @RequestMapping("/getShopOrderItemPropertiesList")
    public String getShopOrderItemPropertiesList(Model model,Long orderId){
        Map<String,Object> map = new HashMap<String,Object>();
        if(orderId!=null){
            map.put("orderId",orderId);
        }
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        List<ShopOrderItemProperties> propList = shopOrderItemPropertiesService.selectMultiShopOrderItemPropertiesList(map);
        Integer total = shopOrderItemPropertiesService.selectMultiShopOrderItemPropertiesCount(map);
        return multiToJson(total,propList);
        /*return JSONObject.toJSONString(propList);*/
    }


}
