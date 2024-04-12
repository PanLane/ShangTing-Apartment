package com.atguigu.shangTingApartment.web.app.service;

import com.atguigu.shangTingApartment.model.entity.RoomInfo;
import com.atguigu.shangTingApartment.web.app.vo.room.RoomDetailVo;
import com.atguigu.shangTingApartment.web.app.vo.room.RoomItemVo;
import com.atguigu.shangTingApartment.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface RoomInfoService extends IService<RoomInfo> {

    /**
     * 分页查询房间列表
     * @param page
     * @param queryVo
     * @return
     */
    Page<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo);

    /**
     * 根据id获取房间的详细信息
     * @param id
     * @return
     */
    RoomDetailVo getDetailById(Long id);

    /**
     * 根据公寓id分页查询房间列表
     * @param id
     * @return
     */
    Page<RoomItemVo> pageItemByApartmentId(Page<RoomItemVo> page,Long id);
}
