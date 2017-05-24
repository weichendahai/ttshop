package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLEncoder;

/**
 * <p>
 * 设置首页banner表 前端控制器
 * </p>
 *
 * @author wangzhen
 * @since 2017-03-23
 */
@Controller
@RequestMapping("/fileupload")
public class FileUploadController {
    @Autowired
   private FileUploadService fileUploadService;
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/getimage")
    public @ResponseBody
    String getImagePath(String imgStr,String sufix) throws Exception{
        String str=fileUploadService.getImagePath(imgStr, sufix);
        return URLEncoder.encode(str, "UTF-8");
    }
}
