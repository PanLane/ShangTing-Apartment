package com.atguigu.shangTingApartment.web.app.mapper;

import com.atguigu.shangTingApartment.model.entity.*;
import com.atguigu.shangTingApartment.web.app.vo.attr.AttrValueVo;
import com.atguigu.shangTingApartment.web.app.vo.fee.FeeValueVo;
import com.atguigu.shangTingApartment.web.app.vo.room.RoomItemVo;
import com.atguigu.shangTingApartment.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.RoomInfo
*/
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

    /**
     * 分页查询房间列表
     * @param page
     * @param queryVo
     * @return
     */
    Page<RoomItemVo> pageItem(Page<RoomItemVo> page,@Param("queryVo") RoomQueryVo queryVo);

    /**
     *根据房间id查询房间属性列表
     * @param id
     * @return
     */
    List<AttrValueVo> selectAttrValueVoList(Long id);

    /**
     * 根据房间id查询房间配套列表
     * @param id
     * @return
     */
    List<FacilityInfo> selectFacilityInfoList(Long id);

    /**
     * 根据房间id查询房间标签列表
     * @param id
     * @return
     */
    List<LabelInfo> selectLabelInfoList(Long id);

    /**
     * 根据房间id查询支付方式列表
     * @param id
     * @return
     */
    List<PaymentType> selectPaymentTypeList(Long id);

    /**
     * 根据房间id查询可选租期列表
     * @param id
     * @return
     */
    List<LeaseTerm> selectLeaseTermList(Long id);

    /**
     * 根据公寓id获取最少租金
     * @param id
     * @return
     */
    Integer selectMinRentByApartmentId(Long id);

    /**
     * 根据公寓id分页查询房间列表
     * @param id
     * @return
     */
    Page<RoomItemVo> selectPageItemByApartmentId(Page<RoomItemVo> page,@Param("id") Long id);
}