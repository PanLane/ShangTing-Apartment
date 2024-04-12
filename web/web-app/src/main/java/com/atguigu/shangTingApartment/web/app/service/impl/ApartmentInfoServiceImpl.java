package com.atguigu.shangTingApartment.web.app.service.impl;

import com.atguigu.shangTingApartment.model.entity.*;
import com.atguigu.shangTingApartment.model.enums.ItemType;
import com.atguigu.shangTingApartment.web.app.mapper.GraphInfoMapper;
import com.atguigu.shangTingApartment.web.app.mapper.RoomInfoMapper;
import com.atguigu.shangTingApartment.web.app.service.ApartmentInfoService;
import com.atguigu.shangTingApartment.web.app.vo.apartment.ApartmentDetailVo;
import com.atguigu.shangTingApartment.web.app.vo.apartment.ApartmentItemVo;
import com.atguigu.shangTingApartment.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.shangTingApartment.web.app.mapper.ApartmentInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    GraphInfoMapper graphInfoMapper;
    @Autowired
    RoomInfoMapper roomInfoMapper;

    @Override
    public ApartmentDetailVo queryDetailById(Long id) {

        //根据id获取公寓基本信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);
        if(apartmentInfo==null) return null;

        //根据公寓id获取公寓图片列表
        LambdaQueryWrapper<GraphInfo> graphWrapper = new LambdaQueryWrapper<>();
        graphWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
        graphWrapper.eq(GraphInfo::getItemId,id);
        List<GraphInfo> graphInfoList = graphInfoMapper.selectList(graphWrapper);

        //根据公寓id获取公寓标签列表
        List<LabelInfo> labelInfoList = apartmentInfoMapper.selectLabelInfoListById(id);

        //根据公寓id获取公寓配套列表
        List<FacilityInfo> facilityInfoList = apartmentInfoMapper.selectFacilityInfoListById(id);

        //根据公寓id获取最少租金
        Integer minRent = roomInfoMapper.selectMinRentByApartmentId(id);

        //封装数据
        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();
        BeanUtils.copyProperties(apartmentInfo,apartmentDetailVo);
        List<GraphVo> graphVoList = new ArrayList<>();
        for (GraphInfo roomGraph : graphInfoList) {
            GraphVo graphVo = new GraphVo();
            graphVo.setName(roomGraph.getName());
            graphVo.setUrl(roomGraph.getUrl());
            graphVoList.add(graphVo);
        }
        apartmentDetailVo.setGraphVoList(graphVoList);
        apartmentDetailVo.setLabelInfoList(labelInfoList);
        apartmentDetailVo.setFacilityInfoList(facilityInfoList);
        apartmentDetailVo.setMinRent(minRent==null?null:BigDecimal.valueOf(minRent));
        apartmentDetailVo.setIsDelete(apartmentInfo.getIsDeleted() == 1);

        return apartmentDetailVo;
    }

    @Override
    public ApartmentItemVo getApartmentItemVoById(Long id) {
        //根据id获取公寓基本信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);
        if(apartmentInfo==null) return null;

        //根据公寓id获取公寓图片列表
        LambdaQueryWrapper<GraphInfo> graphWrapper = new LambdaQueryWrapper<>();
        graphWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
        graphWrapper.eq(GraphInfo::getItemId,id);
        List<GraphInfo> graphInfoList = graphInfoMapper.selectList(graphWrapper);

        //根据公寓id获取公寓标签列表
        List<LabelInfo> labelInfoList = apartmentInfoMapper.selectLabelInfoListById(id);

        //根据公寓id获取最少租金
        Integer minRent = roomInfoMapper.selectMinRentByApartmentId(id);

        ApartmentItemVo apartmentItemVo = new ApartmentItemVo();
        apartmentItemVo.setGraphVoList(graphInfoList);
        apartmentItemVo.setLabelInfoList(labelInfoList);
        apartmentItemVo.setMinRent(minRent==null?null:BigDecimal.valueOf(minRent));
        BeanUtils.copyProperties(apartmentInfo,apartmentItemVo);
        return apartmentItemVo;
    }
}




