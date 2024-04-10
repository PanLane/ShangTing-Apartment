package com.atguigu.shangTingApartment.web.admin.service;

import com.atguigu.shangTingApartment.model.entity.LeaseAgreement;
import com.atguigu.shangTingApartment.web.admin.vo.agreement.AgreementQueryVo;
import com.atguigu.shangTingApartment.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {

    /**
     * 根据id查询租约详细信息
     * @param id
     * @return
     */
    AgreementVo getDetailById(Long id);

    /**
     * 根据条件分页查询租约列表
     * @param page
     * @param queryVo
     * @return
     */
    Page<AgreementVo> customPage(Page<AgreementVo> page, AgreementQueryVo queryVo);
}
