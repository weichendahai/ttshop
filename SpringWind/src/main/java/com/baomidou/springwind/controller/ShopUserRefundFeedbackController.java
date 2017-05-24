package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.ShopUserRefundFeedback;
import com.baomidou.springwind.entity.po.PoShopUserRefundFeedback;
import com.baomidou.springwind.service.IShopUserRefundFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @since 2017-04-25
 */
@Controller
@RequestMapping("/operate/refund/feedback")
public class ShopUserRefundFeedbackController extends BaseController {

    @Autowired
    private IShopUserRefundFeedbackService shopUserRefundFeedbackService;

    @ResponseBody
    @Permission("8002")
    @RequestMapping("/detail")
    public  String selectDetail(Integer userRefundId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userRefundId",userRefundId);
        List<PoShopUserRefundFeedback> feedbacks = shopUserRefundFeedbackService.selectUserRefundFeedbackList(map);
        return toJson(feedbacks);
    }
}
