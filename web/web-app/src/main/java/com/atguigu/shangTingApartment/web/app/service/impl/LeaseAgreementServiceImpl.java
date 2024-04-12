package com.atguigu.shangTingApartment.web.app.service.impl;

import com.atguigu.shangTingApartment.model.entity.LeaseAgreement;
import com.atguigu.shangTingApartment.web.app.service.LeaseAgreementService;
import com.atguigu.shangTingApartment.web.app.vo.agreement.AgreementDetailVo;
import com.atguigu.shangTingApartment.web.app.vo.agreement.AgreementItemVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.shangTingApartment.web.app.mapper.LeaseAgreementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {

    @Autowired
    LeaseAgreementMapper leaseAgreementMapper;

    @Override
    public List<AgreementItemVo> listItem(String phone) {
        return leaseAgreementMapper.selectListItem(phone);
    }

    @Override
    public AgreementDetailVo getDetailById(Long id) {
        return leaseAgreementMapper.selectDetailById(id);
    }
}




