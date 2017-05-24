package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ShopBrand;
import com.baomidou.springwind.entity.vo.ShopBrandVO;
import com.baomidou.springwind.mapper.ShopBrandMapper;
import com.baomidou.springwind.service.IShopBrandService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
@Service
public class ShopBrandServiceImpl extends BaseServiceImpl<ShopBrandMapper, ShopBrand> implements IShopBrandService {

    @Override
    public List<ShopBrandVO> selectAllBrandVO() {
        List<ShopBrandVO> list = baseMapper.selectAllBrandVO();
        return list;
    }

    @Override
    public List<ShopBrand> selectByManufactorId(Integer mid) {
        return baseMapper.selectByManufactorId(mid);
    }


}
