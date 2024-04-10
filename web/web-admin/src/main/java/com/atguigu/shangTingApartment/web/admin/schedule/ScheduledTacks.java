package com.atguigu.shangTingApartment.web.admin.schedule;

import com.atguigu.shangTingApartment.model.entity.LeaseAgreement;
import com.atguigu.shangTingApartment.model.enums.LeaseStatus;
import com.atguigu.shangTingApartment.web.admin.service.LeaseAgreementService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTacks {

    @Autowired
    private LeaseAgreementService leaseAgreementService;

    //每天0:0:0检查租期是否到期
    @Scheduled(cron = "000***")
    public void checkLeaseStatus(){
        LambdaUpdateWrapper<LeaseAgreement> wrapper = new LambdaUpdateWrapper<>();
        wrapper.gt(LeaseAgreement::getLeaseEndDate,new Date());
        wrapper.in(LeaseAgreement::getStatus, LeaseStatus.SIGNED,LeaseStatus.WITHDRAWING,LeaseStatus.RENEWING);
        wrapper.set(LeaseAgreement::getStatus,LeaseStatus.EXPIRED);
        //如果在租约期内，并且已到期，则将状态修改该为已到期
        leaseAgreementService.update(wrapper);
    }
}
