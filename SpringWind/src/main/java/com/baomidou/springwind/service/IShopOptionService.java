package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ShopOption;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 商城参数 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface IShopOptionService extends IService<ShopOption> {
    /**
     * 购物倒计时时间，单位秒
     */
	public static Integer CartCDValue = 1;
    /**
     * 运费，单位分
     */
    public static Integer Freight = 2;
    /**
     * 免运费金额，单位分
     */
    public static Integer FreeFreightFee = 3;
    /**
     * 分享朋友圈标题
     */
    public static Integer SharedMomentsTitle = 4;
    /**
     * 分享朋友圈描述
     */
    public static Integer SharedMomentsDesc = 5;
    /**
     * 爆款列表商品数量
     */
    public static Integer HotItemConut = 6;


}
