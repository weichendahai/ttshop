package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopItemPropertyName;
import com.baomidou.springwind.service.IShopItemPropertyNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 商品规格维护表，此表用于维护规格，将规格 前端控制器
 * </p>
 *
 * @author Woody
 * @since 2017-05-02
 */
@Controller
@RequestMapping("/item/shopItemPropertyName")
public class ShopItemPropertyNameController  extends BaseController {
	@Autowired
    IShopItemPropertyNameService shopItemPropertyNameService;

    @Permission("5006")
    @RequestMapping("/list")
    public String list(Model model) {
        return "/item/propertyNameList";
    }

    @ResponseBody
    @Permission("5006")
    @RequestMapping("/getPropertyNameList")
    public String getManufactorList() {
        Page<ShopItemPropertyName> page = getPage();
        return jsonPage(shopItemPropertyNameService.selectPage(page, null));
    }


    //跳转到添加或编辑页面
    @Permission("5006")
    @RequestMapping("/edit")
    public  String edit(Model model,Long propertyKey){
        if ( propertyKey != null ) {
            model.addAttribute("propertyName", shopItemPropertyNameService.selectById(propertyKey));
        }
        return "/item/propertyNameEdit";
    }

    //添加或编辑商品提交操作
    @ResponseBody
    @Permission("5006")
    @RequestMapping("/addOrEditSubmit")
    public String addOrEditSubmit(ShopItemPropertyName propertyName){
        boolean edit = shopItemPropertyNameService.insertOrUpdate(propertyName);
        return  callbackSuccess(edit);
    }


}
