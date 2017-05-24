package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopUserFilterScheme;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Woody
 * @since 2017-05-01
 */
public interface ShopUserFilterSchemeMapper extends BaseMapper<ShopUserFilterScheme> {
    List<ShopUserFilterScheme> selectAll();
}