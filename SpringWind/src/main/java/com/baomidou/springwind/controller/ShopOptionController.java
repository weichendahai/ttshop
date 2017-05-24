package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopOption;
import com.baomidou.springwind.service.IShopOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 商城参数 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/option")
public class ShopOptionController extends BaseController{

    @Autowired
    IShopOptionService shopOptionService;

    @Permission("9001")
    @RequestMapping("/list")
    public String list(Model model) {
        return "/option/list";
    }

    @ResponseBody
    @Permission("9001")
    @RequestMapping("/getOptionList")
    public String getOptionmList() {
        Page<ShopOption> page = getPage();
        return jsonPage(shopOptionService.selectPage(page,null));
    }

    //添加或者编辑商品用户
    @ResponseBody
    @Permission("9001")
    @RequestMapping("/edit")
    public String edit(Integer optionKey ,String newValue){
        if (optionKey == null || optionKey == 0 || newValue ==null  ||newValue.equals("")){
            return callbackSuccess(false);
        }
        ShopOption shopOption =  shopOptionService.selectById(optionKey);
        shopOption.setOptionValue(newValue);
        shopOptionService.insertOrUpdate(shopOption);
        return callbackSuccess(true);
    }
}
