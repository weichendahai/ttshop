package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.ShopFeedback;
import com.baomidou.springwind.entity.po.PoShopFeedback;
import com.baomidou.springwind.service.IShopFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Woody
 * @since 2017-05-05
 */
@Controller
@RequestMapping("/service")
public class ShopFeedbackController extends BaseController {

    @Autowired
    private IShopFeedbackService shopFeedbackService;

    @Permission("10001")
    @RequestMapping("/list")
    public String getList(Model model){
        return "/service/list";
    }

    @Permission("10001")
    @RequestMapping("/reply")
    public String reply(Model model,Integer feedbackId){
        if(feedbackId!=null) {
            ShopFeedback theme = shopFeedbackService.selectById(feedbackId);
            model.addAttribute("theme",theme);
            List<PoShopFeedback> feedbacks = shopFeedbackService.selectShopFeedbackMessageList(feedbackId);
            model.addAttribute("feedbacks",feedbacks);
            shopFeedbackService.updateShopFeedbackByFid(feedbackId);
        }
        return "/service/reply";
    }

    @ResponseBody
    @Permission("10001")
    @RequestMapping("/getShopFeedbackList")
    public String getShopFeedbackList(String searchKey){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        map.put("fid",0);
        if(searchKey!=null){
            map.put("searchKey",searchKey.trim());
        }
        List<PoShopFeedback> feedbacks = shopFeedbackService.selectShopFeedbackThemeList(map);
        Integer total = shopFeedbackService.selectShopFeedbackThemeCount(map);
        return multiToJson(total,feedbacks);
    }

    @ResponseBody
    @Permission("10001")
    @RequestMapping("/servicereply")
    public String serviceReply(ShopFeedback shopFeedback){
        shopFeedback.setImgAddr(null);
        boolean add = shopFeedbackService.insertShopFeedback(shopFeedback);
        return Boolean.toString(add);
    }

    //显示图片
    @Permission("10001")
    @RequestMapping("/showimage")
    public String showImage(Model model,Integer feedbackId){
        ShopFeedback feedback = shopFeedbackService.selectById(feedbackId);
        model.addAttribute("feedback",feedback);
        return "/service/showimage";
    }
}
