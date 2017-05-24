package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 商城用户表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopUserMapper extends BaseMapper<ShopUser> {

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

    /**
     *1查询所有的用户ID
     * @return
     */
    String[] selectUserIdAll();

    /**
     * 2根据用户的订单数量
     * @param x
     * @return
     */
    String[] selectUserIdByOrderCount(@Param("x") Integer x);
    /**
     * 3根据用户的点评数量
     * @param x
     * @return
     */
    String[] selectUserIdByEvaluateCount(@Param("x") Integer x);
    /**
     * 4根据用户的分享数量
     * @param x
     * @return
     */
    String[] selectUserIdBySharedCount(@Param("x") Integer x);
    /**
     * 5根据用户的消费金额
     * @param x
     * @return
     */
    String[] selectUserIdByOrderTotal(@Param("x") Integer x);
    /**
     * 6根据用户的订单数量排名
     * @param x
     * @return
     */
    String[] selectUserIdByOrderCountRank(@Param("x") Integer x);
    /**
     * 7根据用户的点评数量排名
     * @param x
     * @return
     */
    String[] selectUserIdByEvaluateCountRank(@Param("x") Integer x);
    /**
     * 8根据用户的分享数量排名
     * @param x
     * @return
     */
    String[] selectUserIdBySharedCountRank(@Param("x") Integer x);
    /**
     * 9根据用户的消费金额排名
     * @param x
     * @return
     */
    String[] selectUserIdByOrderTotalRank(@Param("x") Integer x);

}