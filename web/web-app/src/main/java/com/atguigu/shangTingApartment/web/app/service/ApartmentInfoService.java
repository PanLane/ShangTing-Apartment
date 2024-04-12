package com.atguigu.shangTingApartment.web.app.service;

import com.atguigu.shangTingApartment.model.entity.ApartmentInfo;
import com.atguigu.shangTingApartment.web.app.vo.apartment.ApartmentDetailVo;
import com.atguigu.shangTingApartment.web.app.vo.apartment.ApartmentItemVo;
import com.atguigu.shangTingApartment.web.app.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {

    /**
     * 根据公寓id获取公寓详细信息
     * @param id
     * @return
     */
    ApartmentDetailVo queryDetailById(Long id);

    /**
     * 根据id获取公寓基本信息
     * @param id
     * @return
     */
    ApartmentItemVo getApartmentItemVoById(Long id);
}
