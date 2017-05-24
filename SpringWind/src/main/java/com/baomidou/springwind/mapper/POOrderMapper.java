package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.po.POOrder;

import java.util.List;

/**
 * Created by Server on 2017/3/29.
 */
public interface POOrderMapper extends BaseMapper<POOrder> {
    List<POOrder> selectPOOrderList();
}
