package com.atguigu.shangTingApartment.web.app.service;

import com.atguigu.shangTingApartment.model.entity.BrowsingHistory;
import com.atguigu.shangTingApartment.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【browsing_history(浏览历史)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface BrowsingHistoryService extends IService<BrowsingHistory> {

    /**
     * 根据用户id获取浏览历史
     * @param page
     * @param userId
     * @return
     */
    Page<HistoryItemVo> customPage(Page<HistoryItemVo> page,String userId);
}
