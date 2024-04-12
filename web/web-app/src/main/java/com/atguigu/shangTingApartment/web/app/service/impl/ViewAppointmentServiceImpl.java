package com.atguigu.shangTingApartment.web.app.service.impl;

import com.atguigu.shangTingApartment.model.entity.ViewAppointment;
import com.atguigu.shangTingApartment.web.app.mapper.ApartmentInfoMapper;
import com.atguigu.shangTingApartment.web.app.service.ApartmentInfoService;
import com.atguigu.shangTingApartment.web.app.service.ViewAppointmentService;
import com.atguigu.shangTingApartment.web.app.vo.apartment.ApartmentItemVo;
import com.atguigu.shangTingApartment.web.app.vo.appointment.AppointmentDetailVo;
import com.atguigu.shangTingApartment.web.app.vo.appointment.AppointmentItemVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.shangTingApartment.web.app.mapper.ViewAppointmentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {

    @Autowired
    ViewAppointmentMapper viewAppointmentMapper;
    @Autowired
    ApartmentInfoService apartmentInfoService;

    @Override
    public List<AppointmentItemVo> listItem(String phone) {
        return viewAppointmentMapper.selectListItem(phone);
    }

    @Override
    public AppointmentDetailVo getDetailById(Long id) {
        //根据id获取租约信息
        ViewAppointment viewAppointment = viewAppointmentMapper.selectById(id);
        if(viewAppointment==null) return null;
        //获取公寓id
        Long apartmentId = viewAppointment.getApartmentId();
        //根据id获取公寓基本信息
        ApartmentItemVo apartmentItemVo = apartmentInfoService.getApartmentItemVoById(apartmentId);

        //组装数据
        AppointmentDetailVo appointmentDetailVo = new AppointmentDetailVo();
        appointmentDetailVo.setApartmentItemVo(apartmentItemVo);
        BeanUtils.copyProperties(viewAppointment,appointmentDetailVo);
        return appointmentDetailVo;
    }
}




