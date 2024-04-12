package com.atguigu.shangTingApartment.web.app.mapper;

import com.atguigu.shangTingApartment.model.entity.ApartmentInfo;
import com.atguigu.shangTingApartment.model.entity.FacilityInfo;
import com.atguigu.shangTingApartment.model.entity.LabelInfo;
import com.atguigu.shangTingApartment.web.app.vo.apartment.ApartmentItemVo;
import com.atguigu.shangTingApartment.web.app.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

    /**
     * 根据公寓id获取公寓标签列表
     * @param id
     * @return
     */
    List<LabelInfo> selectLabelInfoListById(Long id);

    /**
     * 根据公寓id获取公寓配套列表
     * @param id
     * @return
     */
    List<FacilityInfo> selectFacilityInfoListById(Long id);


    /**
     * 根据id获取公寓杂费信息
     * @param id
     * @return
     */
    List<FeeValueVo> selectFeeValueVoList(Long id);
}




