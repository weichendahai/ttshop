package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ShopUserFilterRecord;
import com.baomidou.springwind.mapper.ShopUserFilterRecordMapper;
import com.baomidou.springwind.service.IShopUserFilterRecordService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
public class ShopUserFilterRecordServiceImpl extends BaseServiceImpl<ShopUserFilterRecordMapper, ShopUserFilterRecord> implements IShopUserFilterRecordService {

    /**
     * @param userIds 用户的ID
     * @param reason 0试用商品筛选，1优惠券筛选，2红包筛选
     * @param schemeId shop_user_filter_scheme表的id
     * @return
     */
    @Override
    public boolean  addShopUserFilterRecordByUserIds(List<Integer> userIds,Integer reason,Integer schemeId){
        ShopUserFilterRecord sufr=null;
        List<ShopUserFilterRecord> list = new ArrayList<ShopUserFilterRecord>();
        for(Integer userId:userIds){
            sufr = new ShopUserFilterRecord();
            /*int userId = Integer.parseInt(s);*/
            sufr.setCreateDate(new Date());
            sufr.setReason(reason);
            sufr.setUserId(userId);
            sufr.setSchemeId(schemeId);
            list.add(sufr);
        }
        boolean addList = insertBatch(list);
        return addList;
    }
}
