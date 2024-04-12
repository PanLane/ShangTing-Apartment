package com.atguigu.shangTingApartment.web.app.controller.appointment;


import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.model.entity.ViewAppointment;
import com.atguigu.shangTingApartment.web.app.context.LoginUserContext;
import com.atguigu.shangTingApartment.web.app.service.ViewAppointmentService;
import com.atguigu.shangTingApartment.web.app.vo.appointment.AppointmentDetailVo;
import com.atguigu.shangTingApartment.web.app.vo.appointment.AppointmentItemVo;
import com.atguigu.shangTingApartment.web.app.vo.user.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "看房预约信息")
@RestController
@RequestMapping("/app/appointment")
public class ViewAppointmentController {

    @Autowired
    ViewAppointmentService viewAppointmentService;

    @Operation(summary = "保存或更新看房预约")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ViewAppointment viewAppointment) {
        return Result.ok(viewAppointmentService.saveOrUpdate(viewAppointment));
    }

    @Operation(summary = "查询个人预约看房列表")
    @GetMapping("listItem")
    public Result<List<AppointmentItemVo>> listItem() {
        //获取登录用户信息
        UserInfoVo userInfoVo = LoginUserContext.get();
        //获取用户手机号
        String phone = userInfoVo.getNickname();
        //根据手机号查询个人预约看房列表
        List<AppointmentItemVo> list = viewAppointmentService.listItem(phone);
        return Result.ok(list);
    }


    @GetMapping("getDetailById")
    @Operation(summary = "根据ID查询预约详情信息")
    public Result<AppointmentDetailVo> getDetailById(Long id) {
        AppointmentDetailVo appointmentDetailVo = viewAppointmentService.getDetailById(id);
        return Result.ok(appointmentDetailVo);
    }

}

