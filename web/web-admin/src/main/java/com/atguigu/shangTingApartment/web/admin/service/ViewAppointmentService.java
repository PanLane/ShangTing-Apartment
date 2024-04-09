package com.atguigu.shangTingApartment.web.admin.service;

import com.atguigu.shangTingApartment.model.entity.ViewAppointment;
import com.atguigu.shangTingApartment.web.admin.vo.appointment.AppointmentQueryVo;
import com.atguigu.shangTingApartment.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ViewAppointmentService extends IService<ViewAppointment> {

    /**
     * 分页查询预约信息
     * @param page
     * @param queryVo
     * @return
     */
    Page<AppointmentVo> customPage(Page<AppointmentVo> page, AppointmentQueryVo queryVo);
}
