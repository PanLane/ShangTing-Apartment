package com.atguigu.shangTingApartment.web.admin.service;

import com.atguigu.lease.model.entity.ApartmentInfo;
import com.atguigu.shangTingApartment.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.shangTingApartment.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.shangTingApartment.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.shangTingApartment.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {

    /**
     * 保存或更新公寓信息
     * @param apartmentSubmitVo
     */
    void customSaveOrUpdate(ApartmentSubmitVo apartmentSubmitVo);

    /**
     * 根据id删除公寓信息，及与之关联的信息
     * @param id
     */
    void customRemoveById(Long id);

    /**
     * 根据公寓id删除与之关联的信息
     * @param id
     */
    void customRemoveRelationData(Long id);

    /**
     * 根据公寓id获取公寓详细信息
     * @param id
     * @return
     */
    ApartmentDetailVo queryDetailById(Long id);

    /**
     * 根据条件分页查询公寓列表
     * @param page
     * @param queryVo
     * @return
     */
    Page<ApartmentItemVo> pageItem(Page<ApartmentItemVo> page, ApartmentQueryVo queryVo);
}
