package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.SysLog;
import com.baomidou.springwind.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 操作日志表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/log")
public class SysLogController extends BaseController{
    @Autowired
    private ISysLogService sysLogService;

    @Permission("4001")
    @RequestMapping("/list")
    public String list(Model model) {
        return "/log/list";
    }

    @ResponseBody
    @Permission("4001")
    @RequestMapping("/getLogList")
    public String getUserList() {
        Page<SysLog> page = getPage();
        return jsonPage(sysLogService.selectPage(page, null));
    }

    @ResponseBody
    @Permission("4001")
    @RequestMapping("/delete/{id}")
    public String delUser(@PathVariable Long id) {
        sysLogService.deleteById(id);
        return Boolean.TRUE.toString();
    }
}
