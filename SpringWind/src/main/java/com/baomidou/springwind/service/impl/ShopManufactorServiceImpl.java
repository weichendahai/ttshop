package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ShopManufactor;
import com.baomidou.springwind.mapper.ShopManufactorMapper;
import com.baomidou.springwind.service.IShopManufactorService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 厂商表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopManufactorServiceImpl extends BaseServiceImpl<ShopManufactorMapper, ShopManufactor> implements IShopManufactorService {
    public List<ShopManufactor> selectAll(){
       return baseMapper.selectAll();
    }
}
