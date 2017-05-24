package com.baomidou.springwind.controller;

import com.baomidou.framework.controller.AjaxResult;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.entity.po.PoShopItem;
import com.baomidou.springwind.entity.vo.ItemEditModel;
import com.baomidou.springwind.entity.vo.ItemPropertyModel;
import com.baomidou.springwind.exception.ShopException;
import com.baomidou.springwind.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商城商品表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/item")
public class ShopItemController extends BaseController {

    @Autowired
    private IShopItemService shopItemService;

    @Autowired
    private IShopManufactorService shopManufactorService;

    @Autowired
    private IShopBrandService shopBrandService;

    @Autowired
    private IShopItemCategoryService shopItemCategoryService;

    @Autowired
    private IShopItemPropertiesService shopItemPropertiesService;

    @Autowired
    private IShopItemPropertyNameService shopItemPropertyNameService;

    @Autowired
    private IShopItemPriceService shopItemPriceService;

    @Permission("5001")
    @RequestMapping("/list")
    public String list(Model model) {

        return "/item/list";
    }

    @ResponseBody
    @Permission("5001")
    @RequestMapping("/getShopItemList")
    public String getShopItemList(String searchKey) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        if(searchKey!=null&&!searchKey.trim().equals("")){
                map.put("searchKey",searchKey.trim());
        }
        /*List<ShopItem> shopItems = shopItemService.selectMultiShopItemCategoryList(map);*/
        List<PoShopItem> poShopItems = shopItemService.selectMultiShopItemList(map);
        Integer total = shopItemService.selectMultiShopItemCategoryCount(map);
        return multiToJson(total,poShopItems);
    }

    //跳转到编辑加页面
    @Permission("5001")
    @RequestMapping("/edit")
    public String edit(Model model,Long itemId){
        EntityWrapper<ShopItemCategory> ew = new EntityWrapper<ShopItemCategory>();
        List<ShopItemCategory> itemCategoryList = null;
        int icp = 1;


        //itemPropertyModels
        List<ShopItemPropertyName> propertyNames = shopItemPropertyNameService.selectByMap(null);
        List<ItemPropertyModel> itemPropertyModels = new ArrayList<ItemPropertyModel>();
        model.addAttribute("itemPropertyModels",itemPropertyModels);

        for(ShopItemPropertyName propertyName : propertyNames){
            ItemPropertyModel itemPropertyModel = new ItemPropertyModel();
            itemPropertyModel.setPropertyKey(propertyName.getPropertyKey());
            itemPropertyModel.setPropertyName(propertyName.getPropertyName());
            itemPropertyModels.add(itemPropertyModel);
        }

        Integer manufactorId = -1;
        if ( itemId != null ) {
            ShopItem si = shopItemService.selectById(itemId);
            model.addAttribute("item", si);
            if (si!=null){
                String infoImgAddrs  = si.getInfoImageAddrs();
                if (infoImgAddrs != null){
                    boolean maxInfoImgAddrs = infoImgAddrs.split(",").length >= 5;
                    model.addAttribute("maxInfoImgAddrs",maxInfoImgAddrs);
                }

                icp = si.getItemCategoryPrimary();
                manufactorId = si.getManufactorId();
            }

            //properties
            Map<String,Object> para = new HashMap<String, Object>(1);
            para.put("item_id",itemId);
            List<ShopItemProperties> propertieses = shopItemPropertiesService.selectByMap(para);
            StringBuffer sbValue = new StringBuffer();
            StringBuffer sbDesc = new StringBuffer();
            for (int i = 0; i < itemPropertyModels.size(); i++) {
                ItemPropertyModel itemPropertyModel = itemPropertyModels.get(i);
                if (propertieses != null ){
                    for (int j = 0; j < propertieses.size(); j++) {
                        ShopItemProperties shopItemProperties = propertieses.get(j);
                        if (shopItemProperties.getPropertyKey() == itemPropertyModel.getPropertyKey()){
                            sbValue.append(shopItemProperties.getPropertyValue());
                            sbValue.append(",");
                            sbDesc.append(shopItemProperties.getPropertyDesc());
                            sbDesc.append(" ");
                        }
                    }
                    if (sbValue.length()>0){
                        sbValue.deleteCharAt(sbValue.length()-1);
                        sbDesc.deleteCharAt(sbDesc.length()-1);
                        itemPropertyModel.setPropertyValues(sbValue.toString());
                        sbValue.delete(0,sbValue.length());
                        itemPropertyModel.setPropertyDescs(sbDesc.toString());
                        sbDesc.delete(0,sbDesc.length());
                    }
                }
            }

            //propertyPrice
            Map<String,Object> pricePara = new HashMap<String, Object>(1);
            pricePara.put("item_id",itemId);
            List<ShopItemPrice> shopItemPrices = shopItemPriceService.selectByMap(pricePara);
            model.addAttribute("shopItemPrices",shopItemPrices);
        }

        ShopItemCategory sic = new ShopItemCategory();
        sic.setPid(icp);
        ew.setEntity(sic);
        itemCategoryList = shopItemCategoryService.selectList(ew);
        model.addAttribute("itemCategoryList",itemCategoryList);
        List<ShopManufactor> manufactors = shopManufactorService.selectAll();
        model.addAttribute("manufactors", manufactors);
        List<ShopBrand> brands = null;
        if (manufactorId == -1){
            brands = shopBrandService.selectByManufactorId(manufactors.get(0).getId());
        }else{
            brands = shopBrandService.selectByManufactorId(manufactorId);
        }
        model.addAttribute("brands", brands);
        return "/item/edit";
    }

    //跳转到查看图片页面
    @Permission("5001")
    @RequestMapping("/viewImg")
    public String viewImg(Model model,String imgAddrs,String scale){
        String[] strs = imgAddrs.split(",");
        if (strs != null && strs.length >0){
            model.addAttribute("imgs", strs);
        }
        if (scale == null || scale == "") scale = "100";
        model.addAttribute("scale",scale);
        return "/item/viewImg";
    }

    //通用搜索商品
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping("/commonSearchItem")
    public String commonSearchItem(Model model,String keyword){
        String keywordStr = "";
        try{
            keywordStr = URLDecoder.decode(keyword,"UTF-8");
        }catch (UnsupportedEncodingException e){
            logger.error(e.getLocalizedMessage());
        }
        List<ShopItem> list =  shopItemService.selectItemByKeywordAllState(keywordStr);
        for(int i =0 ; i < list.size()&&list != null;++i){
            ShopItem si = list.get(i);
            si.setItemCompose(shopManufactorService.selectById(si.getManufactorId()).getManufactor());//借用并未使用的字段传值
        }
        return callback(new AjaxResult(true,"success..",list));
    }

    @ResponseBody
    @Permission("5001")
    @RequestMapping("/brandsByManufactorId")
    public String brandsByManufactorId(Model model,Integer manufactorId){
        if ( manufactorId != null ) {
            List list = shopBrandService.selectByManufactorId(manufactorId);
            return callback(new AjaxResult(true,"success..",list));
        }
        return callbackFail("server is error.");
    }


    //添加或者编辑商品用户
    @ResponseBody
    @Permission("5001")
    @RequestMapping("/addOrEditSubmit")
    public String addOrEditSubmit(ItemEditModel model) {

        ShopItem shopItem = model.getShopItem();
        Integer itemId = shopItem.getId();
        if(itemId!=null){
            shopItem.setItemState(-1);
        }
        shopItemService.insertOrUpdate(shopItem);
        Integer dbItemId = shopItem.getId();
        //update item propertyes
        Map<String,Object> propertyPara = new HashMap<String, Object>(1);
        propertyPara.put("item_id",dbItemId);
        if (itemId != null){
            List<ShopItemProperties> delShopItemProperties = shopItemPropertiesService.selectByMap(propertyPara);
//            for (int i = 0; i < delShopItemProperties.size(); i++) {//redis
//                shopItemPropertiesService.delShopItemProperties(delShopItemProperties.get(i));
//            }
            shopItemPropertiesService.deleteByMap(propertyPara);
        }
        if (model.getItemPropertyModels() != null && model.getItemPropertyModels().size() != 0){
            List<ItemPropertyModel> itemPropertyModels = model.getItemPropertyModels();
            List<ShopItemProperties> dbInsertList = new ArrayList<ShopItemProperties>();
            for(int i = 0 ; i < itemPropertyModels.size() ; ++i ){
                ItemPropertyModel ipm = itemPropertyModels.get(i);
                if (ipm.getPropertyKey() == null) continue;//不能够跳跃，必须从0开始映射，因此必须要过滤
                String[] valueArr = ipm.getPropertyValues().split(",");
                String[] descArr = ipm.getPropertyDescs().split(" ");
                for (int j = 0; j < valueArr.length; j++) {
                    ShopItemProperties sip = new ShopItemProperties();
                    dbInsertList.add(sip);
                    sip.setItemId(dbItemId);
                    sip.setPropertyKey(ipm.getPropertyKey());
                    sip.setPropertyValue(Integer.parseInt(valueArr[j]));
                    sip.setPropertyDesc(descArr[j]);
                }
            }
            shopItemPropertiesService.insertBatch(dbInsertList);
//            for (int i = 0; i < dbInsertList.size(); i++) {//redis
//                shopItemPropertiesService.addShopItemPropertiesToRedis(dbInsertList.get(i));
//            }
        }
        //update price
        if (itemId != null){
            List<ShopItemPrice> dbShopItemPrices = shopItemPriceService.selectByMap(propertyPara);
            //TODO delete from redis

            shopItemPriceService.deleteByMap(propertyPara);
        }
        List<ShopItemPrice> shopItemPrices = model.getShopItemPrices();
        if (shopItemPrices != null && shopItemPrices.size() !=0 ){
            for (int i = 0; i < shopItemPrices.size(); i++) {
                ShopItemPrice sip = shopItemPrices.get(i);
                sip.setItemId(dbItemId);
            }
            shopItemPriceService.insertBatch(shopItemPrices);
            //TODO add to redis
        }

        return callbackSuccess(true);
    }


    //商品的上下架
    @ResponseBody
    @Permission("5001")
    @RequestMapping(value = "/updatestate",method = {RequestMethod.POST})
    public String changeItemState(@RequestBody ShopItem shopItem){
        boolean update=false;
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("item_id",shopItem.getId());
        List<ShopItemPrice> itemPrices = shopItemPriceService.selectByMap(map);
        map.put("property_key",1);
        List<ShopItemProperties> propertiesKey1 = shopItemPropertiesService.selectByMap(map);
        map.put("property_key",2);
        List<ShopItemProperties> propertiesKey2 = shopItemPropertiesService.selectByMap(map);
        map.put("property_key",3);
        List<ShopItemProperties> propertiesKey3 = shopItemPropertiesService.selectByMap(map);
        map.put("property_key",4);
        List<ShopItemProperties> propertiesKey4 = shopItemPropertiesService.selectByMap(map);
        int size = 0;
        int pk1  = propertiesKey1.size();
        int pk2 = propertiesKey2.size();
        int pk3 = propertiesKey3.size();
        int pk4 = propertiesKey4.size();
        if(pk1>0){
            if(pk2>0){
                if(pk3>0){
                    if(pk4>0){
                        size = pk1*pk2*pk3*pk4;
                    }else {
                        size = pk1*pk2*pk3;
                    }
                }else {
                    size = pk1*pk2;
                }
            }else {
                size = pk1;
            }
        }else {
            if(pk2>0){
                if(pk3>0){
                    if(pk4>0){
                        size = pk2*pk3*pk4;
                    }else {
                        size = pk2*pk3;
                    }
                }else {
                    size = pk2;
                }
            }else {
                if(pk3>0){
                    if(pk4>0){
                        size = pk3*pk4;
                    }else {
                        size =pk3;
                    }
                }else {
                   if(pk4>0){
                       size = pk4;
                   }else {
                       size = 0;
                   }
                }
            }
        }
        ShopItem item = shopItemService.selectById(shopItem.getId());
        if(shopItem.getItemState()==1){
            if(size>0){
                    if(size==itemPrices.size()){
                        if(null!=item.getItemIconAddr()){
                            if(null!=item.getPrice()){
                                if(null!=item.getEvaluateBonusValue()){
                                    if(null!=item.getSharedBonusValue()){
                                        update =  shopItemService.updateById(shopItem);
                                        return callbackSuccess(update);
                                    }else {
                                        return callbackFail("请设置此商品的分享佣金!!");
                                    }
                                }else {
                                    return callbackFail("请设置此商品的点评佣金!!");
                                }
                            }else {
                                return callbackFail("请设置此商品的通用价格!!");
                            }
                        }else {
                            return callbackFail("请设置此商品的图标!!");
                        }
                    }else {
                        return callbackFail("请对此商品设置正确的规格价格!!");
                    }
            }else {
                return callbackFail("请对此商品设置正确的规格!!");
            }
        }else {
            update =  shopItemService.updateById(shopItem);
            return callbackSuccess(update);
        }
    }

    @ResponseBody
    @Permission("5001")
    @RequestMapping(value = "/updateItemState",method = {RequestMethod.POST})
    public String updateItemState(@RequestBody ShopItem shopItem){

        Integer itemId = shopItem.getId();
        if (itemId == null || itemId <=0 ){
            return callbackFail("没有找到需要操作的商品!!");
        }
        Integer itemState = shopItem.getItemState();
        if (itemState == null ){
            logger.error("商品状态不正确!! 状态码:"+ itemState + ", itemId = " + itemId);
            return callbackFail("商品状态不正确!!");
        }

        ShopItem shopItemDb = shopItemService.selectById(itemId);
        if (shopItemDb == null ){
            return callbackFail("没有找到需要操作的商品!!");
        }

        if (shopItemDb.getItemState() == IShopItemService.StatePutOn){
            try {
                shopItemService.putItemOff(shopItemDb);
                return callbackSuccess(true);
            } catch (ShopException e) {
                logger.error("商品下架失败 ， itemId = " + itemId);
                return callbackFail(e.getMessage());
            }
        }

        if (shopItemDb.getItemState() == IShopItemService.StateCreate || shopItemDb.getItemState() == IShopItemService.StatePutOff){
            try {
                shopItemService.putItemOn(shopItemDb);
                return callbackSuccess(true);
            } catch (ShopException e) {
                logger.error("商品上架失败 ， itemId = " + itemId);
                return callbackFail(e.getMessage());
            }
        }
        return callbackFail("修改商品状态失败，请联系管理员确认问题");
    }


    //删除商品
    @ResponseBody
    @Permission("5001")
    @RequestMapping("/delItem/{itemId}")
    public String delItem(@PathVariable Integer itemId) {
        boolean del = false;
        if(itemId!=null){
            ShopItem si = new ShopItem();
            si.setId(itemId);
            si.setItemState(-2);
            del = shopItemService.updateById(si);
        }
        return Boolean.toString(del);
    }

    //功效列表
    @ResponseBody
    @Permission("5002")
    @RequestMapping("/effect/getShopItemEffectList")
    public String getShopItemEffectList() {
        Page<ShopItem> page = getPage();
        page.setOrderByField("sortFactor");
        return jsonPage(shopItemService.selectPage(page, null));
    }

    //功效编辑跳转
    @Permission("5002")
    @RequestMapping("/effect/edit")
    public String editEffect(Model model,Long itemId){
        if ( itemId != null ) {
            model.addAttribute("item", shopItemService.selectById(itemId));
        }
        return "/effect/edit";
    }

    //编辑商品
    @ResponseBody
    @Permission("5002")
    @RequestMapping("/effect/editItemEffect")
    public String editItemEffect(ShopItem item){
        boolean edit = false;
        if(item!=null){
            if(item.getId()!=null){
                edit = shopItemService.insertOrUpdate(item);
            }
        }
        return  callbackSuccess(edit);
    }
}
