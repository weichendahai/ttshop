package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopGiftCard;
import com.baomidou.springwind.entity.ShopHotItem;
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
 * 红包表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/operate/card")
public class ShopGiftCardController extends BaseController {

    @Autowired
    private IShopGiftCardService shopGiftCardService;

    @Permission("8004")
    @RequestMapping("/{html}")
    public String list(Model model,@PathVariable("html") String html) {
        if (html != null && html != "") {
            if(html.contains("list")){
                return "/card/list";
            }
            if(html.contains("add")){
                return "/card/add";
            }
        }
        return "/error/404";
    }

    @ResponseBody
    @Permission("8004")
    @RequestMapping("/getShopGiftCardList")
    public String getShopGiftCardList(){
    /*    Page<ShopGiftCard> page = getPage();
    return toJson(shopGiftCardService.selectPage(page,null));*/
        List<ShopGiftCard> list = shopGiftCardService.selectList(null);
        JSONObject jo = new JSONObject();
        jo.put("total",list.size());
        jo.put("rows",list);
        return toJson(jo) ;
    }

    @ResponseBody
    @Permission("8004")
    @RequestMapping("/addShopGiftCard")
    public String addShopGiftCard(ShopGiftCard shopGiftCard){
        boolean add =false;
        if(shopGiftCard!=null){
            add = shopGiftCardService.insert(shopGiftCard);
        }
        return callbackSuccess(add);
    }
}
