package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopBrand;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.vo.ShopBrandVO;

import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author Woody
 * @since 2017-03-21
 */
public interface IShopBrandService extends IService<ShopBrand> {
    public List<ShopBrandVO> selectAllBrandVO() ;

    public List<ShopBrand> selectByManufactorId(Integer mid);

}
