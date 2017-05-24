package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopUserRefund;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.po.PoShopUserRefund;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户退款记录表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-23
 */
public interface IShopUserRefundService extends IService<ShopUserRefund> {
    List<PoShopUserRefund> selectPoUserRefundList();
     //申请退款
    String requestRefund(String user_id, String order_id, String item_id, String refund_type, String refund_reason, String refund_info, String refund_image_addr,String order_item_id);
    //获取退款记录列表
    String getreFundList(String user_id, String page_no);

    /**
     * 查询所有退款列表
     * @param map
     * @return
     */
    List<PoShopUserRefund> selectMultiShopUserRefundList(Map<String,Object> map);

    /**
     * 查询所有退款列表的数量
     * @param map
     * @return
     */
    Integer selectMultiShopUserRefundCount(Map<String,Object> map);

    /**
     * 查询退款详情
     * @param refundId
     * @return
     */
    PoShopUserRefund selectShopUserRefundDetailById(Integer refundId);

}
