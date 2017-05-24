package com.baomidou.springwind.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.POOrder;

import javax.jnlp.BasicService;
import java.util.List;

/**
 * Created by Server on 2017/3/29.
 */
public interface IPOOrderService extends IService<POOrder> {
    List<POOrder> selectPOOrderList();
}
