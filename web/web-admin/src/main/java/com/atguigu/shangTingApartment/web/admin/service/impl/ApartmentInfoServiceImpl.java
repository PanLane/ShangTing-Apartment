package com.atguigu.shangTingApartment.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.shangTingApartment.common.exceptions.ApartmentCustomException;
import com.atguigu.shangTingApartment.common.result.ResultCodeEnum;
import com.atguigu.shangTingApartment.web.admin.mapper.ApartmentInfoMapper;
import com.atguigu.shangTingApartment.web.admin.service.*;
import com.atguigu.shangTingApartment.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.shangTingApartment.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.shangTingApartment.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.shangTingApartment.web.admin.vo.apartment.ApartmentSubmitVo;
import com.atguigu.shangTingApartment.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    ApartmentFacilityService apartmentFacilityService;
    @Autowired
    ApartmentLabelService apartmentLabelService;
    @Autowired
    ApartmentFeeValueService apartmentFeeValueService;
    @Autowired
    GraphInfoService graphInfoService;
    @Autowired
    ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    RoomInfoService roomInfoService;

    @Override
    public void customSaveOrUpdate(ApartmentSubmitVo apartmentSubmitVo) {
        //根据id判断是否为修改操作
        boolean isUpdate = apartmentSubmitVo.getId()!=null;
        //保存或修改公寓信息
        saveOrUpdate(apartmentSubmitVo);
        //获取公寓id
        Long id = apartmentSubmitVo.getId();
        //如果是修改操作，删除与之关联的关系信息
        if(isUpdate) customRemoveRelationData(id);
        //批量保存配套关系
        List<Long> facilityInfoIds = apartmentSubmitVo.getFacilityInfoIds();
        if(!CollectionUtils.isEmpty(facilityInfoIds)){
            List<ApartmentFacility> apartmentFacilities = new ArrayList<>();
            for(Long facilityId:facilityInfoIds){
                ApartmentFacility apartmentFacility = ApartmentFacility.builder().apartmentId(id).facilityId(facilityId).build();
                apartmentFacilities.add(apartmentFacility);
            }
            apartmentFacilityService.saveBatch(apartmentFacilities);
        }
        //批量保存标签关系
        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if(!CollectionUtils.isEmpty(labelIds)){
            List<ApartmentLabel> apartmentLabels = new ArrayList<>();
            for(Long labelId:labelIds){
                ApartmentLabel apartmentLabel = ApartmentLabel.builder().apartmentId(id).labelId(labelId).build();
                apartmentLabels.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(apartmentLabels);
        }
        //批量保存杂费关系
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if(!CollectionUtils.isEmpty(feeValueIds)){
            List<ApartmentFeeValue> apartmentFeeValues = new ArrayList<>();
            for (Long feeValueId : feeValueIds) {
                ApartmentFeeValue apartmentFeeValue = ApartmentFeeValue.builder().apartmentId(id).feeValueId(feeValueId).build();
                apartmentFeeValues.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValues);
        }
        //批量保存图片
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if(!CollectionUtils.isEmpty(graphVoList)){
            List<GraphInfo> graphInfos = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfo.setItemId(id);
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }
    }

    @Override
    public void customRemoveById(Long id) {
        //判断该公寓下是否有与之关联的房间
        LambdaQueryWrapper<RoomInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoomInfo::getApartmentId,id).eq(RoomInfo::getIsRelease,1);
        boolean has_room = roomInfoService.count(wrapper)>0;
        if(has_room) throw new ApartmentCustomException(ResultCodeEnum.DELETE_ERROR);
        //删除与之关联的关系信息
        customRemoveRelationData(id);
        //删除公寓信息
        removeById(id);
    }

    @Override
    public void customRemoveRelationData(Long id) {
        //删除配套关系
        LambdaQueryWrapper<ApartmentFacility> facilityWrapper = new LambdaQueryWrapper<>();
        facilityWrapper.eq(ApartmentFacility::getApartmentId,id);
        apartmentFacilityService.remove(facilityWrapper);
        //删除标签关系
        LambdaQueryWrapper<ApartmentLabel> labelWrapper = new LambdaQueryWrapper<>();
        labelWrapper.eq(ApartmentLabel::getApartmentId,id);
        apartmentLabelService.remove(labelWrapper);
        //删除杂费关系
        LambdaQueryWrapper<ApartmentFeeValue> feeWrapper = new LambdaQueryWrapper<>();
        feeWrapper.eq(ApartmentFeeValue::getApartmentId,id);
        apartmentFeeValueService.remove(feeWrapper);
        //删除图片
        LambdaQueryWrapper<GraphInfo> graphWrapper = new LambdaQueryWrapper<>();
        graphWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
        graphWrapper.eq(GraphInfo::getItemId, id);
        graphInfoService.remove(graphWrapper);
    }

    @Override
    public ApartmentDetailVo queryDetailById(Long id) {
        return apartmentInfoMapper.selectDetailById(id);
    }

    @Override
    public Page<ApartmentItemVo> pageItem(Page<ApartmentItemVo> page, ApartmentQueryVo queryVo) {
        return apartmentInfoMapper.selectPageItem(page,queryVo);
    }
}




