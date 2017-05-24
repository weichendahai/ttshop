package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopBrand;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.vo.ShopBrandVO;

import java.util.List;

/**
 * <p>
  * 品牌表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopBrandMapper extends BaseMapper<ShopBrand> {
    List<ShopBrandVO> selectAllBrandVO();
    List<ShopBrand> selectByManufactorId(Integer mid);
}