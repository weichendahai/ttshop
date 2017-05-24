package com.baomidou.springwind.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.ShopBanner;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.po.PoBanner;
import com.baomidou.springwind.service.IShopBannerService;
import com.baomidou.springwind.service.IShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设置首页banner表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/operate/banner")
public class ShopBannerController extends BaseController {

    @Autowired
    private IShopBannerService shopBannerService;

    @Autowired
    private IShopItemService shopItemService;

    @Permission("8003")
    @RequestMapping("/list")
    public String list(){
        return "/banner/list";
    }

    /*@Permission("8003")
    @RequestMapping("/edit")
    public String edit1(Model model,ModelAndView modelAndView,Integer bannerId){

        return "/banner/edit";
    }*/

    @Permission("8003")
    @RequestMapping("/edit")
    public String edit(Model model,Integer bannerId){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("bannerId",bannerId);
        List<ShopBanner> banners = shopBannerService.selectMultiShopBanner(map);
        if(banners!=null&&banners.size()>0){
            model.addAttribute("banner",banners.get(0));
        }
        return "/banner/edit";
    }

    @Permission("8003")
    @RequestMapping("/add")
    public String add(Model model){

        return "/banner/add";
    }

    @ResponseBody
    @Permission("8003")
    @RequestMapping("/addBanner")
    public String addBanner(ShopBanner banner){
        boolean add = false;
        if(banner.getId()!=null){
            add =   shopBannerService.updateById(banner);
        }else {
            add =   shopBannerService.insert(banner);
            shopBannerService.addbannerList(banner);
        }
        return callbackSuccess(add);

    }

    //获取添加轮播商品名称列表
    @ResponseBody
    @Permission("8003")
    @RequestMapping("/itemList")
    public String getItemList(){
        /*List<ShopItem> items = shopItemService.selectList(null);*/
        /*return JSONUtils.toJSONString(items);*/
        List<ShopBanner> banners = shopBannerService.selectItemNameBannerList(null);
        return toJson(banners);
    }

    @ResponseBody
    @Permission("8003")
    @RequestMapping("/delBanner")
    public String delBanner(Integer bannerId){
        boolean del=false;
       if(bannerId!=null){
           ShopBanner shopBanner = new ShopBanner();
           shopBanner.setId(bannerId);
           del = shopBannerService.deleteById(bannerId);
           shopBannerService.delbannerList(shopBanner);
       }
       return Boolean.toString(del);
    }

    //获取轮播商品列表
    @ResponseBody
    @Permission("8003")
    @RequestMapping("/getShopBannerList")
    public String getShopBannerList(){
        Map<String,Object> map = new HashMap<String, Object>();
        List<ShopBanner> banners = shopBannerService.selectMultiShopBanner(map);
        Integer total = shopBannerService.selectMultiShopBannerCount();
        return multiToJson(total,banners);
    }
    @ResponseBody
    @Permission("8003")
    @RequestMapping("/editBanner")
    public String editBanner(ShopBanner shopBanner){
        /*Map<String,Object> map = new HashMap<String, Object>();
        List<ShopBanner> banners = shopBannerService.selectMultiShopBanner(map,getLimitIndex(),getLimitSize());
        Integer total = shopBannerService.selectMultiShopBannerCount();
        return multiToJson(total,banners);*/
        boolean edit=false;
        if(shopBanner.getId()!=null){
           edit= shopBannerService.updateById(shopBanner);
        }
      return   callbackSuccess(edit);
    }
}
