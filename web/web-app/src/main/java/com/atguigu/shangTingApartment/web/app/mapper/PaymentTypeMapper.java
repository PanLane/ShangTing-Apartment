package com.atguigu.shangTingApartment.web.app.mapper;

import com.atguigu.shangTingApartment.model.entity.PaymentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【payment_type(支付方式表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.PaymentType
*/
public interface PaymentTypeMapper extends BaseMapper<PaymentType> {

    /**
     * 根据房间id获取可选支付方式列表
     * @param roomId
     * @return
     */
    List<PaymentType> selectListByRoomId(Long roomId);
}




