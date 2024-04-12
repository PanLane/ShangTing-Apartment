package com.atguigu.shangTingApartment.web.app.service.impl;

import com.atguigu.shangTingApartment.model.entity.*;
import com.atguigu.shangTingApartment.model.enums.ItemType;
import com.atguigu.shangTingApartment.model.enums.LeaseStatus;
import com.atguigu.shangTingApartment.web.app.mapper.*;
import com.atguigu.shangTingApartment.web.app.service.ApartmentInfoService;
import com.atguigu.shangTingApartment.web.app.service.RoomInfoService;
import com.atguigu.shangTingApartment.web.app.vo.apartment.ApartmentItemVo;
import com.atguigu.shangTingApartment.web.app.vo.attr.AttrValueVo;
import com.atguigu.shangTingApartment.web.app.vo.fee.FeeValueVo;
import com.atguigu.shangTingApartment.web.app.vo.graph.GraphVo;
import com.atguigu.shangTingApartment.web.app.vo.room.RoomDetailVo;
import com.atguigu.shangTingApartment.web.app.vo.room.RoomItemVo;
import com.atguigu.shangTingApartment.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    RoomInfoMapper roomInfoMapper;
    @Autowired
    ApartmentInfoService apartmentInfoService;
    @Autowired
    ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    GraphInfoMapper graphInfoMapper;
    @Autowired
    LeaseAgreementMapper leaseAgreementMapper;

    @Override
    public Page<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo) {
        return roomInfoMapper.pageItem(page,queryVo);
    }

    @Override
    public RoomDetailVo getDetailById(Long id) {
        //根据房间id查询房间信息
        RoomInfo roomInfo = roomInfoMapper.selectById(id);
        //获取公寓id
        Long apartmentId = roomInfo.getApartmentId();
        //根据公寓id查询公寓基本信息
        ApartmentItemVo apartmentItemVo = apartmentInfoService.getApartmentItemVoById(apartmentId);
        //根据房间id查询房间属性列表
        List<AttrValueVo> attrValueVoList = roomInfoMapper.selectAttrValueVoList(id);
        //根据房间id查询房间配套列表
        List<FacilityInfo> facilityInfoList = roomInfoMapper.selectFacilityInfoList(id);
        //根据房间id查询房间标签列表
        List<LabelInfo> labelInfoList = roomInfoMapper.selectLabelInfoList(id);
        //根据房间id查询支付方式列表
        List<PaymentType> paymentTypeList = roomInfoMapper.selectPaymentTypeList(id);
        //根据房间id查询可选租期列表
        List<LeaseTerm> leaseTermList = roomInfoMapper.selectLeaseTermList(id);
        //根据房间id查询杂费列表
        List<FeeValueVo> feeValueVoList = apartmentInfoMapper.selectFeeValueVoList(id);
        //判断房间是否以入住
        LambdaQueryWrapper<LeaseAgreement> countWrapper = new LambdaQueryWrapper<>();
        countWrapper.eq(LeaseAgreement::getRoomId,id)
                .eq(LeaseAgreement::getStatus,LeaseStatus.SIGNED)
                .eq(LeaseAgreement::getStatus,LeaseStatus.WITHDRAWING)
                .eq(LeaseAgreement::getStatus,LeaseStatus.RENEWING);
        Long count = leaseAgreementMapper.selectCount(countWrapper);

        boolean isCheckIn = count>0;


        //根据房间id查询房间图片
        LambdaQueryWrapper<GraphInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
        wrapper.eq(GraphInfo::getItemId,id);
        List<GraphVo> roomGraphs = new ArrayList<>();
        for (GraphInfo graphInfo : graphInfoMapper.selectList(wrapper)) {
            GraphVo graphVo = new GraphVo();
            graphVo.setUrl(graphInfo.getUrl());
            graphVo.setName(graphInfo.getName());
            roomGraphs.add(graphVo);
        }

        //封装信息
        RoomDetailVo roomDetailVo = new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo,roomDetailVo);
        roomDetailVo.setApartmentItemVo(apartmentItemVo);
        roomDetailVo.setAttrValueVoList(attrValueVoList);
        roomDetailVo.setFacilityInfoList(facilityInfoList);
        roomDetailVo.setLabelInfoList(labelInfoList);
        roomDetailVo.setPaymentTypeList(paymentTypeList);
        roomDetailVo.setLeaseTermList(leaseTermList);
        roomDetailVo.setGraphVoList(roomGraphs);
        roomDetailVo.setFeeValueVoList(feeValueVoList);
        roomDetailVo.setIsDelete(roomInfo.getIsDeleted() == 1);
        roomDetailVo.setIsCheckIn(isCheckIn);

        //返回
        return roomDetailVo;
    }

    @Override
    public Page<RoomItemVo> pageItemByApartmentId(Page<RoomItemVo> page,Long id) {
        return roomInfoMapper.selectPageItemByApartmentId(page,id);
    }
}




