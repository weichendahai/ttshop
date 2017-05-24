package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopGiftCard;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 红包表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopGiftCardService extends IService<ShopGiftCard> {
    //获取红包
     String getGiftCard(String user_id, String page_no,String state );

}
