package com.atguigu.shangTingApartment.web.admin.mapper;

import com.atguigu.shangTingApartment.model.entity.ApartmentInfo;
import com.atguigu.shangTingApartment.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.shangTingApartment.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.shangTingApartment.web.admin.vo.apartment.ApartmentQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.atguigu.lease.model.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

    /**
     * 根据公寓id获取公寓详细信息
     * @param id
     * @return
     */
    ApartmentDetailVo selectDetailById(Long id);

    /**
     * 根据条件分页查询公寓列表
     * @param page
     * @param queryVo
     * @return
     */
    Page<ApartmentItemVo> selectPageItem(Page<ApartmentItemVo> page,@Param("queryVo") ApartmentQueryVo queryVo);
}




