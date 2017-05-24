package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ShopUserFilterScheme;
import com.baomidou.springwind.mapper.ShopUserFilterSchemeMapper;
import com.baomidou.springwind.service.IShopUserFilterSchemeService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Woody
 * @since 2017-05-01
 */
@Service
public class ShopUserFilterSchemeServiceImpl extends BaseServiceImpl<ShopUserFilterSchemeMapper, ShopUserFilterScheme> implements IShopUserFilterSchemeService {
    @Override
    public List<ShopUserFilterScheme> selectAll() {
        return baseMapper.selectAll();
    }
}
