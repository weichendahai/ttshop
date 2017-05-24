package com.baomidou.springwind.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopFeedback;
import com.baomidou.springwind.entity.ShopHotItem;
import com.baomidou.springwind.service.IShopHotItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Wrapper;
import java.util.*;

/**
 * <p>
 * 爆款商品，大多数都是商品的冗余字段，应该存放在Redis中 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
@Controller
@RequestMapping("/operate/hotitem")
public class ShopHotItemController extends BaseController {

    @Autowired
    private IShopHotItemService shopHotItemService;

    @Permission("8001")
    @RequestMapping("/list")
    public String list(Model model){
        return "/hotitem/list";
    }

    //获取上一期爆款商品的列表
    @ResponseBody
    @Permission("8001")
    @RequestMapping("/getShopHotItemList")
    public String getHotItemList(Integer _index,Integer _size){
        if(_index!=null&&_size!=null){
            List<ShopHotItem> list =  shopHotItemService.selectShopHotItemLimit(_index, _size);
            JSONObject jo = null;
            if(list!=null&&list.size()>0){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("session_no",list.get(0).getSessionNo());
                List<ShopHotItem> hotItemList = shopHotItemService.selectByMap(map);
                jo = new JSONObject();
                jo.put("total",hotItemList.size());
                jo.put("rows", list );
                return toJson(jo);
            }
        }
         return"{'':''}";
    }

    //更新爆款商品列表
    @ResponseBody
    @Permission("8001")
    @RequestMapping("/selectUpdateShopHotItemList")
    public String selectUpdateShopHotItemList(ModelAndView modelAndView,Integer _index,Integer _size){
       List<ShopHotItem> list = shopHotItemService.selectUpdateShopHotItemList();
        JSONObject jo = new JSONObject();
        jo.put("total",list.size());
        jo.put("rows",list);
        return toJson(jo) ;
    }

    //添加跳转页面
    @Permission("8001")
    @RequestMapping("/add")
    public String add(){
        return "/hotitem/add";
    }

    //添加跳转页面
    @Permission("8001")
    @RequestMapping("/update")
    public String update(){

        return "/hotitem/update";
    }

    @ResponseBody
    @Permission("8001")
    @RequestMapping( value = "/addShopHotItem",method = {RequestMethod.POST})
    public String addShopHotItem(@RequestBody List<ShopHotItem> hotItems){
       boolean add = false;
        if(hotItems!=null&&hotItems.size()>0){
            /*for(int i=0;i<hotItems.size();i++){
                hotItems.get(i).setId(null);
            }*/
            if(hotItems.size()==1){
                add = shopHotItemService.insert(hotItems.get(0));
            }
            if(hotItems.size()>1){
                add = shopHotItemService.insertBatch(hotItems);
            }
            shopHotItemService.addHotItemListToRedis(hotItems);
        }
       return callbackSuccess(add);
    }

    //根据id和期数删除爆款商品
    @ResponseBody
    @Permission("8001")
    @RequestMapping("/delShopHotItem")
    public String delShopHotItem(Integer id,Integer sessionNo){
        boolean del = false;
        if(id!=null){
            if(sessionNo!=null){
               del =  shopHotItemService.delShopHotItemByIdAndSessionNo(id,sessionNo);
            }
        }
        return callbackSuccess(del);
    }

    @ResponseBody
    @Permission("8001")
    @RequestMapping("/getShopHotItemListGroup")
    public String selectHotItemSessionNoAndDate(){
        List<ShopHotItem> hotItemList = shopHotItemService.selectHotItemSessionNoAndDate();
        JSONObject jo = new JSONObject();
        jo.put("total",hotItemList.size());
        jo.put("rows", hotItemList );
        return toJson(jo);
    }


    @Permission("8001")
     @RequestMapping("/detail")
     public String detail(Model model){
        return "/hotitem/detail";
    }

    @Permission("8001")
    @RequestMapping("/edit")
    public String edit(Model model){
        return "/hotitem/edit";
    }

    @ResponseBody
    @Permission("8001")
    @RequestMapping("/getHotItemDetailList")
    public String getHotItemDetailList(Integer sessionNo){
        EntityWrapper<ShopHotItem> entity = new EntityWrapper<ShopHotItem>();
        entity.eq("session_no",sessionNo);
        ShopHotItem hotItem = new ShopHotItem();
        hotItem.setSessionNo(sessionNo);
        Page<ShopHotItem> page = getPage();
        Page<ShopHotItem> hotItemPage = shopHotItemService.selectPage(page, entity);
        return jsonPage(hotItemPage);
    }
}
