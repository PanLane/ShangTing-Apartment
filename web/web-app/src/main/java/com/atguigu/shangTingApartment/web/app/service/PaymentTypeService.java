package com.atguigu.shangTingApartment.web.app.service;

import com.atguigu.shangTingApartment.model.entity.PaymentType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【payment_type(支付方式表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface PaymentTypeService extends IService<PaymentType> {

    /**
     * 根据房间id获取可选支付方式列表
     * @param roomId
     * @return
     */
    List<PaymentType> listByRoomId(Long roomId);
}
