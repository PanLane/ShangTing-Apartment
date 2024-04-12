package com.atguigu.shangTingApartment.web.app.service.impl;

import com.atguigu.shangTingApartment.model.entity.PaymentType;
import com.atguigu.shangTingApartment.model.entity.RoomPaymentType;
import com.atguigu.shangTingApartment.web.app.mapper.RoomPaymentTypeMapper;
import com.atguigu.shangTingApartment.web.app.service.PaymentTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.shangTingApartment.web.app.mapper.PaymentTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【payment_type(支付方式表)】的数据库操作Service实现
* @createDate 2023-07-26 11:12:39
*/
@Service
public class PaymentTypeServiceImpl extends ServiceImpl<PaymentTypeMapper, PaymentType>
    implements PaymentTypeService {

    @Autowired
    PaymentTypeMapper paymentTypeMapper;

    @Override
    public List<PaymentType> listByRoomId(Long roomId) {
        return paymentTypeMapper.selectListByRoomId(roomId);
    }
}




