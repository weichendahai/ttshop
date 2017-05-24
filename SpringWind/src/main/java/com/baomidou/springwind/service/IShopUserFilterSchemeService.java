package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopUserFilterScheme;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Woody
 * @since 2017-05-01
 */
public interface IShopUserFilterSchemeService extends IService<ShopUserFilterScheme> {
	List<ShopUserFilterScheme> selectAll();
}
