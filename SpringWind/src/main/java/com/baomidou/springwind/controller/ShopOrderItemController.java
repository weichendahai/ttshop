package com.baomidou.springwind.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopItemProperties;
import com.baomidou.springwind.entity.ShopOrderItem;
import com.baomidou.springwind.entity.ShopOrderItemProperties;
import com.baomidou.springwind.entity.po.PoShopOrderItem;
import com.baomidou.springwind.entity.po.PoShopOrderItemProperties;
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
 * 订单商品关系表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/order")
public class ShopOrderItemController extends BaseController {

    @Autowired
    private IShopOrderItemService shopOrderItemService;

    @Autowired
    private IShopOrderService shopOrderService;

    @Autowired
    private IShopOrderItemPropertiesService shopOrderItemPropertiesService;

    @Autowired
    private IShopItemPropertiesService shopItemPropertiesService;

    @Autowired
    private IShopItemCategoryService shopItemCategoryService;

    @Autowired
    private IShopItemService shopItemService;

    @Permission("7001")
    @RequestMapping("/orderitem")
    public String orderItemList(Model model){

        return "/order/orderitem";
    }

    @ResponseBody
    @Permission("7001")
    @RequestMapping("/getShopOrderItemList")
    public String getShopOrderItemList(Long orderId){
        //此方法在Mybatis中绑定语句异常，先使用orderItem的id
        /*List<ShopOrderItem> list = shopOrderItemService.selectOrderItemListByOrderId(orderId);
        return JSONUtils.toJSONString(list);*/
        Page<ShopOrderItem> page = getPage();
        return jsonPage(shopOrderItemService.selectPage(page));
    }
    //获取订单详情
    @ResponseBody
    @Permission("7001")
    @RequestMapping("/getShopOrderItem")
    public String getShopOrderItem(Long orderId){
        /*Map<String,Object> map = new HashMap<String,Object>();
        if(orderId!=null){
            map.put("orderId",orderId);
        }
        List<ShopOrderItem> orderItems = shopOrderItemService.selectMultiOrderItemList(map);
        List<PoShopOrderItem> poShopOrderItems = new ArrayList<PoShopOrderItem>();
        if(orderItems!=null&&orderItems.size()>0){
            for(int i=0;i<orderItems.size();i++){
                PoShopOrderItem poShopOrderItem = new PoShopOrderItem();
                poShopOrderItem.setCount(orderItems.get(i).getCount());
                poShopOrderItem.setPrice(orderItems.get(i).getPrice());
                poShopOrderItem.setItemId(orderItems.get(i).getItemId());
                 map  = new HashMap<String, Object>();
                map.put("itemId",orderItems.get(i).getItemId());
                List<ShopItemProperties> propertieses = shopItemPropertiesService.selectByMap(map);
                StringBuilder sb = new StringBuilder();
                for(ShopItemProperties sip:propertieses){
                    sb.append(sip.getPropertyDesc()+" ");
                }
                ShopItem item = shopItemService.selectById(orderItems.get(i).getItemId());
                poShopOrderItem.setShopItemCategory(shopItemCategoryService.selectById(item.getItemCategoryPrimary()));
                poShopOrderItem.getShopItemProperties().setPropertyDesc(sb.toString());
                poShopOrderItems.add(poShopOrderItem);
            }
        }*/
        Map<String,Object> map = new HashMap<String,Object>();
        if(orderId!=null){
            map.put("orderId",orderId);
        }
        List<PoShopOrderItem> poShopOrderItems = shopOrderItemService.selectMultiOrderItemList(map);
        return toJson(poShopOrderItems);
    }

}
