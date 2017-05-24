package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.po.PoShopEvaluateBonus;
import com.baomidou.springwind.entity.po.PoShopEvaluateShared;
import com.baomidou.springwind.service.IShopEvaluateBonusService;
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
 * 点评佣金表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/operate/evaluate/detail")
public class ShopEvaluateBonusController extends BaseController {

    @Autowired
    private IShopEvaluateBonusService shopEvaluateBonusService;

    @ResponseBody
    @Permission("8007")
    @RequestMapping("/bonus")
    public String list(Model model,Long evaluateId) {
            Map<String,Object> map = new HashMap<String, Object>();
            if(evaluateId!=null){
                map.put("evaluateId",evaluateId);
                List<PoShopEvaluateBonus> bonuses = shopEvaluateBonusService.selectMultiShopEvaluateSharedList(map);
                return toJson(bonuses);
            }
            return "['':'']";
    }

}
