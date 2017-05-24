package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopHotSearchKeyword;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 热搜榜 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopHotSearchKeywordMapper extends BaseMapper<ShopHotSearchKeyword> {
   //获取所有的热搜词
    List<String> selectByKeyword();
}