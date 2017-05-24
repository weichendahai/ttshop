package com.baomidou.springwind.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopUser;
import com.baomidou.springwind.service.IShopUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Wrapper;
import java.util.*;

/**
 * <p>
 * 商城用户表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/shopuser")
public class ShopUserController extends BaseController{
	@Autowired
    private IShopUserService shopUserService;

    @Permission("6001")
    @RequestMapping("/list")
    public String list(Model model){
        return "/shopuser/list";
    }

    @ResponseBody
    @Permission("6001")
    @RequestMapping("/getShopUserList")
    public String getShopUserList(String searchKey){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        map.put("deleted",0);//地址状态，0：正常，1：删除
        if(searchKey!=null&&!searchKey.equals("")){
            searchKey=searchKey.trim();
            if(searchKey.trim().matches("[0-9]{0,11}")){
                map.put("mobilePhone",searchKey);
            }else {
                map.put("searchKey",searchKey);
            }
        }
        List<ShopUser> shopUsers = shopUserService.selectMultiShopUserList(map);
        Integer total = shopUserService.selectMultiShopUserCount(map);
        return multiToJson(total,shopUsers);
    }
    //查找
    @ResponseBody
    @Permission("6001")
    @RequestMapping("/getSearchShopUserList/{searchKey}")
    public String getSearchShopUserList(@PathVariable String searchKey){
        Page<ShopUser> page = getPage();
        return jsonPage(shopUserService.selectPage(page,null));
    }

    //获取排序的ShopUser列表
    @ResponseBody
    @Permission("6001")
    @RequestMapping("/getSortShopUserList/{sortField}")
    public String getSortShopUserList(@PathVariable String sortField){
        Page<ShopUser> page = getPage();
        if(StringUtils.isNotBlank(sortField)&&StringUtils.isNotEmpty(sortField)){
            page.setOrderByField(sortField);
            return jsonPage(shopUserService.selectPage(page,null));
        }
        return jsonPage(shopUserService.selectPage(page,null));
    }


    //跳转到页面

    @Permission("6001")
    @RequestMapping("/edit")
    public String edit( Model model, Long shopUserId ) {
        Map<String,Object> map = new HashMap<String,Object>();
        if ( shopUserId != null ) {
            map.put("shopUserId",shopUserId);
            List<ShopUser> shopUsers = shopUserService.selectMultiShopUserList(map);
            if(shopUsers!=null&&shopUsers.size()==1){
                model.addAttribute("shopUser",shopUsers.get(0) );
            }
        }
        return "/shopuser/edit";
    }

    //编辑商品用户
    @ResponseBody
    @Permission("6001")
    @RequestMapping("/editShopUser")
    public String editShopUser(ShopUser shopUser){
        boolean edit = false;
        if(shopUser!=null){
            if(shopUser.getId()!=null){
                edit = shopUserService.updateById(shopUser);
            }
        }
      return  callbackSuccess(edit);
    }
    //删除前台用户
    @ResponseBody
    @Permission("6001")
    @RequestMapping("/delShopUser/{shopUserId}")
    public String delShopUser(@PathVariable Long shopUserId){
        if(shopUserId!=null){
           boolean del =  shopUserService.deleteById(shopUserId);
            return Boolean.toString(del);
        }
        return "false";
    }

    //跳转到添加页面
    @Permission("6001")
    @RequestMapping("/add")
    public String add(Model model){

        return "/shopuser/add";
    }

    //添加商品用户
    @ResponseBody
    @Permission("6001")
    @RequestMapping("/addShopUser")
    public String addShopUser(ShopUser shopUser) {
        boolean add = false;
        if(shopUser!=null){
            if(shopUser.getId()==null) {
                add = shopUserService.insert(shopUser);
            }
        }
        return  callbackSuccess(add);
    }
}
