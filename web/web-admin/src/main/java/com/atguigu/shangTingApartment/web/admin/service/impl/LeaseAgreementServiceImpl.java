package com.atguigu.shangTingApartment.web.admin.service.impl;

import com.atguigu.shangTingApartment.model.entity.*;
import com.atguigu.shangTingApartment.web.admin.mapper.*;
import com.atguigu.shangTingApartment.web.admin.service.LeaseAgreementService;
import com.atguigu.shangTingApartment.web.admin.vo.agreement.AgreementQueryVo;
import com.atguigu.shangTingApartment.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {

    @Autowired
    LeaseAgreementMapper leaseAgreementMapper;
    @Autowired
    ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    RoomInfoMapper roomInfoMapper;
    @Autowired
    PaymentTypeMapper paymentTypeMapper;
    @Autowired
    LeaseTermMapper leaseTermMapper;

    @Override
    public AgreementVo getDetailById(Long id) {
        //根据租约id查询租约信息
        LeaseAgreement leaseAgreement = leaseAgreementMapper.selectById(id);
        //获取公寓id
        Long apartmentId = leaseAgreement.getApartmentId();
        //根据公寓id查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(apartmentId);
        //获取房间id
        Long roomId = leaseAgreement.getRoomId();
        //根据房间id查询房间信息
        RoomInfo roomInfo = roomInfoMapper.selectById(roomId);
        //获取支付类型id
        Long paymentTypeId = leaseAgreement.getPaymentTypeId();
        //根据支付类型id查询支付类型信息
        PaymentType paymentType = paymentTypeMapper.selectById(paymentTypeId);
        //获取租期id
        Long leaseTermId = leaseAgreement.getLeaseTermId();
        //根据租期id查询租期信息
        LeaseTerm leaseTerm = leaseTermMapper.selectById(leaseTermId);

        //组装数据
        AgreementVo agreementVo = new AgreementVo();
        BeanUtils.copyProperties(leaseAgreement,agreementVo);
        agreementVo.setApartmentInfo(apartmentInfo);
        agreementVo.setRoomInfo(roomInfo);
        agreementVo.setPaymentType(paymentType);
        agreementVo.setLeaseTerm(leaseTerm);

        //返回
        return agreementVo;
    }

    @Override
    public Page<AgreementVo> customPage(Page<AgreementVo> page, AgreementQueryVo queryVo) {
        return leaseAgreementMapper.selectCustomPage(page,queryVo);
    }
}




