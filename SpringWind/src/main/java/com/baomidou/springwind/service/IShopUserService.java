package com.baomidou.springwind.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.ShopUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商城用户表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopUserService extends IService<ShopUser> {
//获取用户基本信息
    String getUserInfo(String user_id);
//修改用户基本信息
    String changeUserInfo(String user_id, int info_type, String new_value);

    /**
     * 条件查询商品用户列表
     * @param map
     * @return
     */
    List<ShopUser> selectMultiShopUserList(Map<String,Object> map);

    /**
     * 根据条件查询商品用户数量
     * @param map
     * @return
     */
    Integer selectMultiShopUserCount(Map<String,Object> map);
    /*根据条件生成500个虚拟账户*/
    void setPitchUser();

    /**
     *10查询所有的用户ID
     * @return
     */
    String selectUserIdAll();

    /**
     * 2根据用户的订单数量
     * @param x
     * @return
     */
    String selectUserIdByOrderCount(Integer x,Integer y);
    /**
     * 3根据用户的点评数量
     * @param x
     * @return
     */
    String selectUserIdByEvaluateCount(Integer x,Integer y);
    /**
     * 4根据用户的分享数量
     * @param x
     * @return
     */
    String selectUserIdBySharedCount( Integer x,Integer y);
    /**
     * 5根据用户的消费金额
     * @param x
     * @return
     */
    String selectUserIdByOrderTotal(Integer x,Integer y);
    /**
     * 6根据用户的订单数量排名
     * @param x
     * @return
     */
    String selectUserIdByOrderCountRank(Integer x);
    /**
     * 7根据用户的点评数量排名
     * @param x
     * @return
     */
    String selectUserIdByEvaluateCountRank(Integer x);
    /**
     * 8根据用户的分享数量排名
     * @param x
     * @return
     */
    String selectUserIdBySharedCountRank( Integer x);
    /**
     * 9根据用户的消费金额排名
     * @param x
     * @return
     */
    String selectUserIdByOrderTotalRank(Integer x);
}
