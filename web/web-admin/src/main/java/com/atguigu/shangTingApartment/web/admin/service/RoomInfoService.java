package com.atguigu.shangTingApartment.web.admin.service;

import com.atguigu.shangTingApartment.model.entity.RoomInfo;
import com.atguigu.shangTingApartment.web.admin.vo.room.RoomDetailVo;
import com.atguigu.shangTingApartment.web.admin.vo.room.RoomItemVo;
import com.atguigu.shangTingApartment.web.admin.vo.room.RoomQueryVo;
import com.atguigu.shangTingApartment.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface RoomInfoService extends IService<RoomInfo> {

    /**
     * 保存或修改房间信息
     * @param roomSubmitVo
     */
    void customSaveOrUpdate(RoomSubmitVo roomSubmitVo);

    /**
     * 根据房间id删除与之关联的信息
     * @param id
     */
    void customRemoveRelationData(Long id);

    /**
     * 根据房间id删除房间信息及与之关联的信息
     * @param id
     */
    void customRemoveById(Long id);

    /**
     * 根据条件分页查询房间列表
     * @param page
     * @param queryVo
     * @return
     */
    Page<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo);

    /**
     * 根据房间id获取房间详细信息
     * @param id
     * @return
     */
    RoomDetailVo getDetailById(Long id);
}
