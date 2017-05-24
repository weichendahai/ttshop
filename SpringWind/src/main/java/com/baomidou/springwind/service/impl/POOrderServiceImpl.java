package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.po.POOrder;
import com.baomidou.springwind.mapper.POOrderMapper;
import com.baomidou.springwind.service.IPOOrderService;
import com.baomidou.springwind.service.IShopOrderService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Server on 2017/3/29.
 */
public class POOrderServiceImpl extends BaseServiceImpl<POOrderMapper,POOrder> implements IPOOrderService {
    @Autowired
    private POOrderMapper poOrderMapper;
    @Override
    public List<POOrder> selectPOOrderList() {
        List<POOrder> poOrders = poOrderMapper.selectPOOrderList();
        return poOrders;
    }
}
