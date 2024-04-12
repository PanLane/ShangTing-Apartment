package com.atguigu.shangTingApartment.web.app.service.impl;

import com.atguigu.shangTingApartment.model.entity.BrowsingHistory;
import com.atguigu.shangTingApartment.web.app.service.BrowsingHistoryService;
import com.atguigu.shangTingApartment.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import com.atguigu.shangTingApartment.web.app.mapper.BrowsingHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【browsing_history(浏览历史)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {

    @Autowired
    BrowsingHistoryMapper browsingHistoryMapper;

    @Override
    public Page<HistoryItemVo> customPage(Page<HistoryItemVo> page,String userId) {
        return browsingHistoryMapper.selectCustomPage(page,userId);
    }
}




