package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopItemCategory;
import com.baomidou.springwind.entity.ShopItemProperties;
import com.baomidou.springwind.service.IShopItemPropertiesService;
import com.baomidou.springwind.service.IShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性表，型号，颜色，容量等 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/item/itemproperties")
public class ShopItemPropertiesController extends BaseController{

    @Autowired
    private IShopItemPropertiesService shopItemPropertiesService;

    @Autowired
    private IShopItemService shopItemService;

    @Permission("5004")
    @RequestMapping("/list")
    public String list(Model model) {
        return "/itemproperties/list";
    }

    //获取商品规格列表
    @ResponseBody
    @Permission("5004")
    @RequestMapping("/getShopItemPropertiesList")
    public String getShopItemPropertiesList(String searchKey) {
        /*Page<ShopItemProperties> page = getPage();
        return jsonPage(shopItemPropertiesService.selectPage(page, null));*/
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        if(searchKey!=null&&!searchKey.equals("")){
            searchKey = searchKey.trim();
            try {
                searchKey = new String(searchKey.getBytes("ISO8859-1"),"UTF-8");
                map.put("searchKey",searchKey);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        List<ShopItemProperties> itemProperties = shopItemPropertiesService.selectMultiShopItemPropertyList(map);
        Integer total = shopItemPropertiesService.selectMultiShopItemPropertyCount(map);
        return multiToJson(total,itemProperties);
    }

    //0，色号
    //1，容量
    //2，数量
    //跳转到编辑添加页面
    @Permission("5004")
    @RequestMapping("/edit")
    public String edit(Model model,Long proId){
        if ( proId != null ) {
            model.addAttribute("itemProperty", shopItemPropertiesService.selectById(proId));
        }
        return "/itemproperties/edit";
    }

    //编辑商品属性
    @ResponseBody
    @Permission("5004")
    @RequestMapping("/editItemProperty")
    public String editShopItemProperty(ShopItemProperties shopItemProperties){
        boolean edit = false;
        if(shopItemProperties!=null){
            if(shopItemProperties.getId()!=null){
                edit = shopItemPropertiesService.updateById(shopItemProperties);
                shopItemPropertiesService.updateitem_properties(shopItemProperties);
            }
        }
        return  callbackSuccess(edit);
    }

    //跳转到添加页面
    @Permission("5004")
    @RequestMapping("/add")
    public String add(Model model){
        List<ShopItem> shopItems = shopItemService.selectList(null);
        model.addAttribute("itemList",shopItems);
        return "/itemproperties/add";
    }

    //添加商品属性
    @ResponseBody
    @Permission("5004")
    @RequestMapping(value = "/addItemProperty",method = {RequestMethod.POST})
    public String addShopItemProperty(@RequestBody List<ShopItemProperties> list) {
        boolean add=false;

        if(list!=null&&list.size()>0){
            Integer itemId = list.get(0).getItemId();
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("item_id",itemId);
            /*增加规格的操作*/
            List<ShopItemProperties> sips = shopItemPropertiesService.selectByMap(map);
            List<Integer> ids = new ArrayList<Integer>();
            if(itemId!=null){
                if(sips!=null&&sips.size()>0){
                    for(ShopItemProperties s:sips){
                        ids.add(s.getId());
                        shopItemPropertiesService.delShopItemProperties(s);
                    }
                    shopItemPropertiesService.deleteBatchIds(ids);
                }
                if(list==null){
                    return callbackSuccess(true);
                }
                add = shopItemPropertiesService.insertBatch(list);
                for(int i=0;i<list.size();i++){
                    shopItemPropertiesService.addShopItemPropertiesToRedis(list.get(i));

                }
            }
            /*if((sips!=null&&sips.size()>0)&&list.size()>=sips.size()){
                for(int i=list.size()-1;i>=0;i--){
                    ShopItemProperties sipNow = list.get(i);
                    for(int j= sips.size()-1;j>=0;j--){
                       if( sipNow.equals(sips.get(j))){
                           list.remove(i);
                           break;
                       }
                    }
                }
                if(list!=null||list.size()==0){
                    return callbackSuccess(true);
                }
            }
            *//*删减规格的操作*//*
            if((sips!=null&&sips.size()>0)&&sips.size()>list.size()){
                List<ShopItemProperties> removeList = new ArrayList<ShopItemProperties>();
                for(int i=sips.size()-1;i>=0;i--){
                    ShopItemProperties sipBefore = sips.get(i);
                    for(int j=list.size()-1;j>=0;j--){
                        if(sipBefore.equals(list.get(j))){
                            ShopItemProperties removeProperty = sips.remove(i);
                            if(i==list.size()-1){
                                break;
                            }
                            list.remove(j);
                            shopItemPropertiesService.delShopItemProperties(removeProperty);
                            break;
                        }
                    }
                }
                if(sips.size()>=1){
                    boolean del = false;
                    for(int ii=0;ii<sips.size();ii++){
                        EntityWrapper<ShopItemProperties> ew = new EntityWrapper<ShopItemProperties>();
                        ShopItemProperties si = new ShopItemProperties();
                        si.setItemId(itemId);
                        si.setPropertyDesc(sips.get(ii).getPropertyDesc());
                        si.setPropertyKey(sips.get(ii).getPropertyValue());
                        ew.setEntity(si);
                      del =  shopItemPropertiesService.delete(ew);
                        shopItemPropertiesService.delShopItemProperties(sips.get(ii));
                    }
                    boolean ad = false;
                    if(list.size()>=1){
                      ad =  shopItemPropertiesService.insertBatch(list);
                    }
                    return callbackSuccess(del&&ad);
                }
            }
            if(list==null){
                return callbackFail("修改失败！");
            }*/
            /*for(ShopItemProperties sip:list){
                shopItemPropertiesService.addShopItemPropertiesToRedis(sip);
            }*/

        }
        return callbackSuccess(add);
    }

    //删除属性
    @ResponseBody
    @Permission("5004")
    @RequestMapping("/delProperty")
    public String delShopItemProperty(Long proId){
        boolean del = false;
        if(proId!=null){
            del = shopItemPropertiesService.deleteById(proId);
            shopItemPropertiesService.addShopItemPropertiesToRedis(shopItemPropertiesService.selectById(proId));
        }
        return  callbackSuccess(del);
    }
}
