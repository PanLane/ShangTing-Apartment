package com.atguigu.shangTingApartment.web.app.mapper;

import com.atguigu.shangTingApartment.model.entity.BrowsingHistory;
import com.atguigu.shangTingApartment.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* @author liubo
* @description 针对表【browsing_history(浏览历史)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.BrowsingHistory
*/
public interface BrowsingHistoryMapper extends BaseMapper<BrowsingHistory> {

    /**
     * 根据用户id获取浏览历史
     * @param page
     * @param userId
     * @return
     */
    Page<HistoryItemVo> selectCustomPage(Page<HistoryItemVo> page,String userId);
}




