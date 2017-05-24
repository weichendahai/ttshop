package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopUserSample;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户试用订单表， 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopUserSampleService extends IService<ShopUserSample> {

    /**试用管理的申请条件
     * 8种筛选条件
     */

    /**
     * 1全随机从用户里选出状态正常的用户的前x单
     * @param x
     * @return
     */
    String autoSample(int x);

    /**
     * 2订单数量下单
     * @param x
     * @param y
     * @return
     */
    String SampleByOrderCount(int x,int y);

    /**
     * 3点评数量大于x的y个用户筛选
     * @param x
     * @param y
     * @return
     */
    String SampleByEvateCount(int x,int y);

    /**
     * 4分享数量大于x的y个用户筛选
     * @param x
     * @param y
     * @return
     */
    String SampleBySharedCount(int x,int y);

    /**
     * 5消费总额大于x元的y个用户筛选
     * @param x
     * @param y
     * @return
     */
    String SampleByMoneyCount(int x,int y);

    /**
     * 6订单量排名倒序选取前x个
     * @param x
     * @return
     */
    String  SampleByOrderTotal(int x);

    /**
     * 7点评量排名倒序选取前x个
     * @param x
     * @return
     */
    String SampleByEvalteTotal(int x);

    /**
     * 8分享量排名倒序选取前x个
     * @param x
     * @return
     */
    String SampleBySharedTotal(int x);

    /**
     * 9消费量排名倒序选取前x个
     * @param x
     * @return
     */
    String  SampleByMoneyTotal(int x);

    /**
     * 自动下单
     * @param shopUserSampleIds
     * @return
     */
    boolean autoAddOrdersByIds(List<Integer> shopUserSampleIds);

    /**
     * 通过userSampleIds查询用户的Id
     * @param userSampleIds
     * @return
     */
    List<Integer> selectUserIdsByUserSampleIds(List<Integer> userSampleIds);

    /**
     * 通过usersampleIds更改申请用户的状态
     * @param userSampleIds
     * @return
     */
    boolean updateShopUserSampleBatchByIds(List<Integer> userSampleIds);
}
