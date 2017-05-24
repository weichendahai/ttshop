package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.mapper.ShopUserMapper;
import com.baomidou.springwind.service.IshopWxService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangzhen on 2017/4/24.
 */
public class ShopWxServiceImpl implements IshopWxService {
    //验证用户是否登录
    @Autowired
    private ShopUserMapper shopUserMapper;

    @Override
    public String isUserLogin(Long timer) {
        return null;
    }

}
