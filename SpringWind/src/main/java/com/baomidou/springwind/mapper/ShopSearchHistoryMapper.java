package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ShopSearchHistory;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 历史搜索记录表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface ShopSearchHistoryMapper extends BaseMapper<ShopSearchHistory> {
    //获取最近的10条记录搜索记录
    List<String> getHistoryByUserId(@Param("user_id") String user_id,@Param("limit") int limit);
   //获取热搜词
    List<String> getHotSearch(@Param("limit")int limit);
}