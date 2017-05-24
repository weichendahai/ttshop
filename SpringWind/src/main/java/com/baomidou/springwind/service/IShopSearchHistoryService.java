package com.baomidou.springwind.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.ShopSearchHistory;

/**
 * <p>
 * 历史搜索记录表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopSearchHistoryService extends IService<ShopSearchHistory> {
    //获取热搜和搜索历史
    String searchPrepare(String user_id);
    //搜索
    String search(String user_id, String keyword,String page_no,String category_id,String order_price,String order_count);
}
