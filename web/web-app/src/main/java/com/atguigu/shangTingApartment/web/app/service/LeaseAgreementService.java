package com.atguigu.shangTingApartment.web.app.service;

import com.atguigu.shangTingApartment.model.entity.LeaseAgreement;
import com.atguigu.shangTingApartment.web.app.vo.agreement.AgreementDetailVo;
import com.atguigu.shangTingApartment.web.app.vo.agreement.AgreementItemVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {

    /**
     * 根据手机号查找个人租约基本信息列表
     * @param phone
     * @return
     */
    List<AgreementItemVo> listItem(String phone);

    /**
     * 根据id获取租约详细信息
     * @param id
     * @return
     */
    AgreementDetailVo getDetailById(Long id);
}
