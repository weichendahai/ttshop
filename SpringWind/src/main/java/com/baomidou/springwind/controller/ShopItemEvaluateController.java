package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopItemEvaluate;
import com.baomidou.springwind.entity.po.PoShopItemEvaluate;
import com.baomidou.springwind.service.IShopItemEvaluateService;
import com.baomidou.springwind.service.IShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户点评表，记录点评信息 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/operate/evaluate")
public class ShopItemEvaluateController extends BaseController{

    @Autowired
    private IShopItemEvaluateService shopItemEvaluateService;

    @Autowired
    private IShopItemService shopItemService;

    @Permission("8007")
    @RequestMapping("/{html}")
    public String getHtml(Model model,@PathVariable("html") String html,Long evaluateId){
        if(html!=null&&html.equals("list")){
            return "/evaluate/list";
        }
        if(html!=null&&html.equals("evaluatedetail")){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("evaluateId",evaluateId);
            List<PoShopItemEvaluate> shopItemEvaluates = shopItemEvaluateService.selectEvaluateListAll(map);
            if(shopItemEvaluates!=null&&shopItemEvaluates.size()==1){
                PoShopItemEvaluate poShopItemEvaluate = shopItemEvaluates.get(0);
                model.addAttribute("shopItemEvaluate",poShopItemEvaluate);
                String itemIds = poShopItemEvaluate.getOtherItemIds();
                if(itemIds!=null){
                    List<ShopItem> items = shopItemService.selectItemListByIds(itemIds);
                    model.addAttribute("items",items);
                }
            }
            return "/evaluate/evaluatedetail";
        }
        return "/error/404";
    }

    //删除点评
    @ResponseBody
    @Permission("8007")
    @RequestMapping("/delEvaluateItem")
    public String delEvaluateItem(ShopItemEvaluate shopItemEvaluate){
        boolean del = false;
        if(shopItemEvaluate!=null){
            shopItemEvaluate.setEvaluateState(1);
            del = shopItemEvaluateService.updateById(shopItemEvaluate);
        }
        return Boolean.toString(del);
    }

    //获取点评列表
    @ResponseBody
    @Permission("8007")
    @RequestMapping("/getShopItemEvaluateList")
    public String searchShopItemEvaluate(String searchKey){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        if(searchKey!=null&&!searchKey.trim().equals("")){
                map.put("searchKey",searchKey.trim());
        }
        List<PoShopItemEvaluate> evaluates = shopItemEvaluateService.selectEvaluateListAll(map);
        Integer total = shopItemEvaluateService.selectEvaluateListAllCount(map);
        return multiToJson(total,evaluates);
    }
}
