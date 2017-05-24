package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.ShopEvaluateShared;
import com.baomidou.springwind.entity.ShopHotSearchKeyword;
import com.baomidou.springwind.entity.po.PoShopEvaluateShared;
import com.baomidou.springwind.entity.po.PoShopSharedBonus;
import com.baomidou.springwind.service.IShopEvaluateSharedService;
import com.baomidou.springwind.service.IShopSharedBonusService;
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
 * 点评的分享记录表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/operate/shared")
public class ShopEvaluateSharedController extends BaseController {

    @Autowired
    private IShopEvaluateSharedService shopEvaluateSharedService;

    @Autowired
    private IShopSharedBonusService shopSharedBonusService;

    @Permission("8009")
    @RequestMapping("/{html}")
    public String list(Model model,@PathVariable("html") String html,Long sharedId) {
        if(html.equals("list")) {
            return "/shared/list";
        }
        if(html.equals("detail")){
            Map<String,Object> map = new HashMap<String, Object>();
            if(sharedId!=null){
                map.put("evaluateSharedId",sharedId);
                List<PoShopEvaluateShared> shareds = shopEvaluateSharedService.selectMultiShopEvaluateShared(map);
                if(shareds!=null&&shareds.size()==1){
                    model.addAttribute("shared",shareds.get(0));
                }
            }
            return "/shared/shareddetail";
        }
        return "/error/404";
    }

    @ResponseBody
    @Permission("8009")
    @RequestMapping("/getShopEvaluateSharedList")
    public String getShopEvaluateSharedList(String searchKey) {
        Map<String,Object> map = new HashMap<String,Object>();
                if(searchKey!=null&&!searchKey.equals("")) {
                    map.put("searchKey", searchKey);
                }
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        List<PoShopEvaluateShared> shareds = shopEvaluateSharedService.selectMultiShopEvaluateShared(map);
        Integer total = shopEvaluateSharedService.selectMultiShopEvaluateSharedCount(map);
        return multiToJson(total,shareds);
    }

    @ResponseBody
    @Permission("8009")
    @RequestMapping("/detail/info")
    public String detail(Model model,Long sharedId) {
        if(sharedId!=null){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("sharedId",sharedId);
            List<PoShopSharedBonus> bonuses = shopSharedBonusService.selectMultiShopSharedBonusList(map);
            return toJson(bonuses);
        }
        return "['':'']";
    }
}
