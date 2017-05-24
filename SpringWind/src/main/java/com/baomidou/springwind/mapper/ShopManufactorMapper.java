package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopManufactor;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 厂商表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopManufactorMapper extends BaseMapper<ShopManufactor> {
    List<ShopManufactor> selectAll();
}