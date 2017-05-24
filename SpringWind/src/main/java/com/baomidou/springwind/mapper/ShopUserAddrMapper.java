package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopUserAddr;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 用户地址表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopUserAddrMapper extends BaseMapper<ShopUserAddr> {
    //获取最大的可用地址
    String selectMaxId(@Param("user_id") String user_id);
}