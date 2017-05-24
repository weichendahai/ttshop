package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopItemCategory;
import com.baomidou.springwind.service.IShopItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类表，配合商品使用 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/item/itemcategory")
public class ShopItemCategoryController extends BaseController {

    @Autowired
    private IShopItemCategoryService shopItemCategoryService;

    @Permission("5003")
    @RequestMapping("/list")
    public String list(Model model) {
        return "/itemcategory/list";
    }

    @ResponseBody
    @Permission("5003")
    @RequestMapping("/getShopItemCategoryList")
    public String getShopItemList() {
        Page<ShopItemCategory> page = getPage();
        return jsonPage(shopItemCategoryService.selectPage(page, null));
        /*Map<String,Object> map = new HashMap<String,Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        List<ShopItemCategory> itemCategories = shopItemCategoryService.selectMultiShopItemCategoryList(map);
        Integer total = shopItemCategoryService.selectMultiShopItemCategoryCount(map);
        return  multiToJson(total,itemCategories);*/
    }

    @ResponseBody
    @Permission("5003")
    @RequestMapping("/getSubCategoryList")
    public String getSubCategoryList() {
       /* EntityWrapper<ShopItemCategory> ew = new EntityWrapper<ShopItemCategory>();
        ShopItemCategory sic = new ShopItemCategory();
        sic.setCategoryLevel(2);
        ew.setEntity(sic);
        List<ShopItemCategory> list = shopItemCategoryService.selectList(ew);
        return JSONObject.toJSONString(list);*/
        List<ShopItemCategory> itemCategories = shopItemCategoryService.selectList(null);
        return toJson(itemCategories);
    }
    //跳转到编辑添加页面
    @Permission("5003")
    @RequestMapping("/edit")
    public String edit(Model model,Long catId){
        if ( catId != null ) {
            model.addAttribute("itemCategory", shopItemCategoryService.selectById(catId));
        }
        return "/itemcategory/edit";
    }

    //编辑分类
    @ResponseBody
    @Permission("5003")
    @RequestMapping("/editItemCategory")
    public String editShopItemCategory(ShopItemCategory shopItemCategory){
        boolean edit = false;
        if(shopItemCategory!=null){
            if(shopItemCategory.getId()!=null){
                edit = shopItemCategoryService.updateById(shopItemCategory);
                shopItemCategoryService.updateCategory(shopItemCategory);
            }
        }
        return  callbackSuccess(edit);
    }

    //跳转到添加页面
    @Permission("5003")
    @RequestMapping("/add")
    public String add(Model model){

        return "/itemcategory/add";
    }

    //添加商品分类
    @ResponseBody
    @Permission("5003")
    @RequestMapping("/addItemCategory")
    public String addShopItemCategory(ShopItemCategory shopItemCategory) {
        boolean add = false;
        if(shopItemCategory!=null){
            add = shopItemCategoryService.insert(shopItemCategory);
            shopItemCategoryService.addCategory(shopItemCategory);
        }
        return  callbackSuccess(add);
    }

    //删除分类
    @ResponseBody
    @Permission("5003")
    @RequestMapping("/delCategory")
    public String delShopItemCategory(Long catId){
        boolean del = false;
        if(catId!=null){
                del = shopItemCategoryService.deleteById(catId);
        }
        return  callbackSuccess(del);
    }
}
