package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ShopUserRefundFeedback;
import com.baomidou.springwind.entity.po.PoShopUserRefundFeedback;
import com.baomidou.springwind.mapper.ShopUserRefundFeedbackMapper;
import com.baomidou.springwind.service.IShopUserRefundFeedbackService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Woody
 * @since 2017-04-25
 */
@Service
public class ShopUserRefundFeedbackServiceImpl extends BaseServiceImpl<ShopUserRefundFeedbackMapper, ShopUserRefundFeedback> implements IShopUserRefundFeedbackService {

    @Override
    public List<PoShopUserRefundFeedback> selectUserRefundFeedbackList(Map<String, Object> map) {
        return baseMapper.selectUserRefundFeedbackList(map);
    }
}
