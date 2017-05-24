package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopManufactor;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 厂商表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopManufactorService extends IService<ShopManufactor> {
	public List<ShopManufactor> selectAll();
}
