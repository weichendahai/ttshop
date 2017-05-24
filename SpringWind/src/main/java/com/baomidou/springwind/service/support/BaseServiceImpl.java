package com.baomidou.springwind.service.support;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
