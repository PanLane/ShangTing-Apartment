package com.atguigu.shangTingApartment.web.app.service;

import com.atguigu.shangTingApartment.model.entity.ViewAppointment;
import com.atguigu.shangTingApartment.web.app.vo.appointment.AppointmentDetailVo;
import com.atguigu.shangTingApartment.web.app.vo.appointment.AppointmentItemVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface ViewAppointmentService extends IService<ViewAppointment> {

    /**
     * 根据手机号查询个人预约看房列表
     * @param phone
     * @return
     */
    List<AppointmentItemVo> listItem(String phone);

    /**
     * 根据ID查询预约详情信息
     * @param id
     * @return
     */
    AppointmentDetailVo getDetailById(Long id);
}
