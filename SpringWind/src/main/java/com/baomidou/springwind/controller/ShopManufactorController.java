package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopManufactor;
import com.baomidou.springwind.service.IShopManufactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 厂商表 前端控制器
 * </p>
 *
 * @author Woody
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/item/manufactor")
public class ShopManufactorController extends BaseController {

    @Autowired
    private IShopManufactorService shopManufactorService;

    @Permission("5004")
    @RequestMapping("/list")
    public String list(Model model) {
        return "/item/manufactorList";
    }

    @ResponseBody
    @Permission("5004")
    @RequestMapping("/getManufactorList")
    public String getManufactorList() {
        Page<ShopManufactor> page = getPage();
        return jsonPage(shopManufactorService.selectPage(page, null));
    }

    //跳转到添加或编辑页面
    @Permission("5004")
    @RequestMapping("/edit")
    public  String edit(Model model,Long manufactorId){
        if ( manufactorId != null ) {
            model.addAttribute("manufactor", shopManufactorService.selectById(manufactorId));
        }
        return "/item/manufactorEdit";
    }

    //添加或编辑商品提交操作
    @ResponseBody
    @Permission("5004")
    @RequestMapping("/addOrEditSubmit")
    public String addOrEditSubmit(ShopManufactor manufactor){
        boolean edit = shopManufactorService.insertOrUpdate(manufactor);
        return  callbackSuccess(edit);
    }

}
