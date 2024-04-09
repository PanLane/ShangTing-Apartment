package com.atguigu.shangTingApartment.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.shangTingApartment.web.admin.mapper.*;
import com.atguigu.shangTingApartment.web.admin.service.*;
import com.atguigu.shangTingApartment.web.admin.vo.attr.AttrValueVo;
import com.atguigu.shangTingApartment.web.admin.vo.graph.GraphVo;
import com.atguigu.shangTingApartment.web.admin.vo.room.RoomDetailVo;
import com.atguigu.shangTingApartment.web.admin.vo.room.RoomItemVo;
import com.atguigu.shangTingApartment.web.admin.vo.room.RoomQueryVo;
import com.atguigu.shangTingApartment.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    RoomInfoMapper roomInfoMapper;
    @Autowired
    RoomAttrValueService roomAttrValueService;
    @Autowired
    RoomFacilityService roomFacilityService;
    @Autowired
    RoomLabelService roomLabelService;
    @Autowired
    RoomPaymentTypeService roomPaymentTypeService;
    @Autowired
    RoomLeaseTermService roomLeaseTermService;
    @Autowired
    GraphInfoService graphInfoService;
    @Autowired
    ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    GraphInfoMapper graphInfoMapper;

    @Override
    public void customSaveOrUpdate(RoomSubmitVo roomSubmitVo) {
        //根据是否有id判断是否是修改操作
        boolean isUpdate = roomSubmitVo.getId()!=null;
        //修改房间信息
        saveOrUpdate(roomSubmitVo);
        //获取房间id
        Long id = roomSubmitVo.getId();
        //如果是修改操作，删除与房间关联的信息
        if(isUpdate) customRemoveRelationData(id);
        //批量保存房间属性对应关系
        List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();
        if(!CollectionUtils.isEmpty(attrValueIds)){
            List<RoomAttrValue> roomAttrValues = new ArrayList<>();
            for (Long attrValueId : attrValueIds) {
                RoomAttrValue roomAttrValue = RoomAttrValue.builder().roomId(id).attrValueId(attrValueId).build();
                roomAttrValues.add(roomAttrValue);
            }
            roomAttrValueService.saveBatch(roomAttrValues);
        }
        //批量保存房间配套对应关系
        List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();
        if(!CollectionUtils.isEmpty(facilityInfoIds)){
            List<RoomFacility> roomFacilities = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIds) {
                RoomFacility roomFacility = RoomFacility.builder().roomId(id).facilityId(facilityInfoId).build();
                roomFacilities.add(roomFacility);
            }
            roomFacilityService.saveBatch(roomFacilities);
        }
        //批量保存房间标签对应关系
        List<Long> labelInfoIds = roomSubmitVo.getLabelInfoIds();
        if(!CollectionUtils.isEmpty(labelInfoIds)){
            List<RoomLabel> roomLabels = new ArrayList<>();
            for (Long labelInfoId : labelInfoIds) {
                RoomLabel roomLabel = RoomLabel.builder().roomId(id).labelId(labelInfoId).build();
                roomLabels.add(roomLabel);
            }
            roomLabelService.saveBatch(roomLabels);
        }
        //批量保存支付方式对应关系
        List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();
        if(!CollectionUtils.isEmpty(paymentTypeIds)){
            List<RoomPaymentType> roomPaymentTypes = new ArrayList<>();
            for (Long paymentTypeId : paymentTypeIds) {
                RoomPaymentType roomPaymentType = RoomPaymentType.builder().roomId(id).paymentTypeId(paymentTypeId).build();
                roomPaymentTypes.add(roomPaymentType);
            }
            roomPaymentTypeService.saveBatch(roomPaymentTypes);
        }
        //批量保存可选租期对应关系
        List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();
        if(!CollectionUtils.isEmpty(leaseTermIds)){
            List<RoomLeaseTerm> roomLeaseTerms = new ArrayList<>();
            for (Long leaseTermId : leaseTermIds) {
                RoomLeaseTerm roomLeaseTerm = RoomLeaseTerm.builder().roomId(id).leaseTermId(leaseTermId).build();
                roomLeaseTerms.add(roomLeaseTerm);
            }
            roomLeaseTermService.saveBatch(roomLeaseTerms);
        }
        //批量保存图片
        List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();
        if(!CollectionUtils.isEmpty(graphVoList)){
            List<GraphInfo> graphInfos = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.ROOM);
                graphInfo.setItemId(id);
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }
    }

    @Override
    public void customRemoveRelationData(Long id) {
        //删除房间属性对应关系
        LambdaQueryWrapper<RoomAttrValue> attrWrapper = new LambdaQueryWrapper<>();
        attrWrapper.eq(RoomAttrValue::getRoomId,id);
        roomAttrValueService.remove(attrWrapper);
        //删除房间配套对应关系
        LambdaQueryWrapper<RoomFacility> facilityWrapper = new LambdaQueryWrapper<>();
        facilityWrapper.eq(RoomFacility::getRoomId,id);
        roomFacilityService.remove(facilityWrapper);
        //删除房间标签对应关系
        LambdaQueryWrapper<RoomLabel> labelWrapper = new LambdaQueryWrapper<>();
        labelWrapper.eq(RoomLabel::getRoomId,id);
        roomLabelService.remove(labelWrapper);
        //删除支付方式对应关系
        LambdaQueryWrapper<RoomPaymentType> paymentTypeWrapper = new LambdaQueryWrapper<>();
        paymentTypeWrapper.eq(RoomPaymentType::getRoomId,id);
        roomPaymentTypeService.remove(paymentTypeWrapper);
        //删除可选租期对应关系
        LambdaQueryWrapper<RoomLeaseTerm> leaseTypeWrapper = new LambdaQueryWrapper<>();
        leaseTypeWrapper.eq(RoomLeaseTerm::getRoomId,id);
        roomLeaseTermService.remove(leaseTypeWrapper);
        //删除图片
        LambdaQueryWrapper<GraphInfo> graphWrapper = new LambdaQueryWrapper<>();
        graphWrapper.eq(GraphInfo::getItemType, ItemType.ROOM).eq(GraphInfo::getItemId,id);
        graphInfoService.remove(graphWrapper);
    }

    @Override
    public void customRemoveById(Long id) {
        //删除与房间关联的信息
        customRemoveById(id);
        //删除房间信息
        roomInfoMapper.deleteById(id);
    }

    @Override
    public Page<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo) {
        return roomInfoMapper.selectPageItem(page,queryVo);
    }

    @Override
    public RoomDetailVo getDetailById(Long id) {
        //根据房间id查询房间信息
        RoomInfo roomInfo = roomInfoMapper.selectById(id);
        //获取公寓id
        Long apartmentId = roomInfo.getApartmentId();
        //根据公寓id查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(apartmentId);
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
        //根据房间id查询图片
        LambdaQueryWrapper<GraphInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GraphInfo::getItemType,ItemType.ROOM);
        wrapper.eq(GraphInfo::getItemId,id);
        List<GraphVo> graphVoList = new ArrayList<>();
        for (GraphInfo graphInfo : graphInfoMapper.selectList(wrapper)) {
            GraphVo graphVo = new GraphVo();
            graphVo.setUrl(graphInfo.getUrl());
            graphVo.setName(graphInfo.getName());
            graphVoList.add(graphVo);
        }

        //封装信息
        RoomDetailVo roomDetailVo = new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo,roomDetailVo);
        roomDetailVo.setApartmentInfo(apartmentInfo);
        roomDetailVo.setAttrValueVoList(attrValueVoList);
        roomDetailVo.setFacilityInfoList(facilityInfoList);
        roomDetailVo.setLabelInfoList(labelInfoList);
        roomDetailVo.setPaymentTypeList(paymentTypeList);
        roomDetailVo.setLeaseTermList(leaseTermList);
        roomDetailVo.setGraphVoList(graphVoList);

        //返回
        return roomDetailVo;
    }
}




