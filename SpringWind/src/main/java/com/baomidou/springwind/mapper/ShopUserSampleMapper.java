package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopSample;
import com.baomidou.springwind.entity.ShopUserSample;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 用户试用订单表， Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopUserSampleMapper extends BaseMapper<ShopUserSample> {
    List<ShopUserSample> selectByUserid(@Param("user_id") String user_id,@Param("page_no") int page_no);
    void applySample(@Param("user_id")String user_id,@Param("sample_id") String sample_id ,@Param("state") String state);
    List<ShopSample> selectAll(@Param("page_no")int page_no);
   /**
   *试用列表的9中策略
    */
   //全随机
    String[] getSampleIds();
   //按照订单数量排序
    String[] SampleByOrderCount(@Param("x") int x);
   //按照点评数量排序
    String[] SampleByEvateCount(@Param("x")int x);
  //按照分享数量大于x
    String[] SampleBySharedCount(@Param("x")int x);
  //按照消费总额
    String[] SampleByMoneyCount(@Param("x")int x);
  //按照订单总量选前x个
    String[] SampleByOrderTotal(@Param("x")int x);
  //点评总量的前x个
    String[] SampleByEvalteTotal(@Param("x")int x);
  //按照分享的前x个
    String[] SampleBySharedTotal(@Param("x")int x);
  //按照消费金额的前x个
    String[] SampleByMoneyTotal(@Param("x")int x);
}