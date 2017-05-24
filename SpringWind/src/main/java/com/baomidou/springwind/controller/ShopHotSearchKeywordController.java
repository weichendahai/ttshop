package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopHotSearchKeyword;
import com.baomidou.springwind.service.IShopHotSearchKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 热搜榜 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/operate/search")
public class ShopHotSearchKeywordController extends BaseController {

    @Autowired
    private IShopHotSearchKeywordService shopHotSearchKeywordService;

    /**
     * 页面跳转
     * @param model
     * @param html
     * @return
     */
    @Permission("8008")
    @RequestMapping("/{html}")
    public String list(Model model,@PathVariable("html") String html,Integer searchId) {
        if (html != null && html != "") {
            if(html.equals("list")){
                return "/search/list";
            }
            if(html.equals("add")){
                return "/search/add";
            }
            if(html.equals("edit")){
                ShopHotSearchKeyword searchKeyword = shopHotSearchKeywordService.selectById(searchId);
                model.addAttribute("shopHotSearchKeyword",searchKeyword);
                return "/search/edit";
            }
        }
        return "/error/404";
    }

    /**
     * 获取列表
     * @return
     */
    @ResponseBody
    @Permission("8008")
    @RequestMapping("/getShopHotSearchKeywordList")
    public String getList() {
        /*List<ShopHotSearchKeyword> keywords = shopHotSearchKeywordService.selectList(null);
        Integer count = shopHotSearchKeywordService.selectCount(null);
        return multiToJson(count,keywords);*/
        Page<ShopHotSearchKeyword> page = new Page<ShopHotSearchKeyword>();
        page.setAsc(true);
        page.setOrderByField("sortFactor");
        Page<ShopHotSearchKeyword> keywordPage = shopHotSearchKeywordService.selectPage(page, null);
        return jsonPage(keywordPage);
    }

    @ResponseBody
    @Permission("8008")
    @RequestMapping("/addKeyword")
    public String addShopHotKeyword(ShopHotSearchKeyword searchKeyword) {
        boolean add = false;
        if(searchKeyword!=null){
            add=   shopHotSearchKeywordService.insert(searchKeyword);
        }
       return callbackSuccess(add);
    }

    @ResponseBody
    @Permission("8008")
    @RequestMapping("/editKeyword")
    public String editShopHotKeyword(ShopHotSearchKeyword searchKeyword) {
        boolean edit = false;
        if(searchKeyword.getId()!=null){
            edit =   shopHotSearchKeywordService.updateById(searchKeyword);
        }
        return callbackSuccess(edit);
    }

    @ResponseBody
    @Permission("8008")
    @RequestMapping("/delKeyword")
    public String delShopHotKeyword(Integer searchId) {
        boolean del = false;
        if(searchId!=null){
            del =   shopHotSearchKeywordService.deleteById(searchId);
        }
        return callbackSuccess(del);
    }
}
