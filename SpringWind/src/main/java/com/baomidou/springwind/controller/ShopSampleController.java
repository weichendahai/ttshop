package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopSample;
import com.baomidou.springwind.entity.ShopUserFilterScheme;
import com.baomidou.springwind.service.IShopItemService;
import com.baomidou.springwind.service.IShopSampleService;
import com.baomidou.springwind.service.IShopUserFilterSchemeService;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试用表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/operate/sample")
public class ShopSampleController extends BaseController {


    @Autowired
    private IShopSampleService shopSampleService;

    @Autowired
    private IShopItemService shopItemService;

    @Autowired
    private IShopUserFilterSchemeService shopUserFilterSchemeService;

    @Permission("8010")
    @RequestMapping("/{html}")
    public String getHtml(Model model,@PathVariable("html") String html,Integer sampleId) {
        if(html!=null&&html.equals("list")){
            return "/sample/list";
        }
        if(html!=null&&html.equals("edit")){
            if(sampleId!=null){
                ShopSample ss = shopSampleService.selectById(sampleId);
                if(ss!=null){
                    model.addAttribute("sample",ss);
                    model.addAttribute("sampleItemName",shopItemService.selectById(ss.getItemId()).getItemName());
                }
            }
            return "/sample/edit";
        }
        return "/error/404";
    }

    @ResponseBody
    @Permission("8010")
    @RequestMapping("/getShopSampleList")
    public String getShopSampleList(String searchKey){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        if(searchKey!=null){
                map.put("searchKey",searchKey);
        }
        List<ShopSample> shopSamples = shopSampleService.selectMultiShopSampleList(map);
        Integer total = shopSampleService.selectMultiShopSampleCount(map);
      return   multiToJson(total,shopSamples);
    }
    //搜索未添加试用的商品名称
    @ResponseBody
    @Permission("8010")
    @RequestMapping("/getSampleNameList")
    public String getSampleNameList(String searchKey){
        Map<String,Object> map = new HashMap<String, Object>();
        List<ShopSample> sampleNames=null;
        if(searchKey!=null){
            searchKey=searchKey.trim();
            map.put("searchKey",searchKey);
            sampleNames = shopSampleService.selectMultiShopSampleItemNameList(map);
            Integer total = shopSampleService.selectMultiShopSampleItemNameCount(map);
            return multiToJson(total, sampleNames);
        }
        return "['':'']";
    }

    @ResponseBody
    @Permission("8010")
    @RequestMapping("/editShopSample")
    public String editShopSample(ShopSample shopSample){
        boolean edit;
        if (shopSample.getId() == null) {
            shopSample.setApplyCount(0);
            shopSample.setCreateDate(new Date());
        }
        edit = shopSampleService.insertOrUpdate(shopSample);
        return callbackSuccess(edit);
    }


    @Permission("8010")
    @RequestMapping("/operApply")
    public String operApply(Model model){
        List<ShopUserFilterScheme> scs = shopUserFilterSchemeService.selectAll();
        model.addAttribute("schemes",scs);
        model.addAttribute("defaultDesc",scs.get(0).getDescription());
        return "/sample/operApply";
    }
}
