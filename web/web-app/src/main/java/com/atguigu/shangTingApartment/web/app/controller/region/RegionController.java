package com.atguigu.shangTingApartment.web.app.controller.region;


import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.model.entity.CityInfo;
import com.atguigu.shangTingApartment.model.entity.DistrictInfo;
import com.atguigu.shangTingApartment.model.entity.ProvinceInfo;
import com.atguigu.shangTingApartment.web.app.service.CityInfoService;
import com.atguigu.shangTingApartment.web.app.service.DistrictInfoService;
import com.atguigu.shangTingApartment.web.app.service.ProvinceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.WeakReference;
import java.util.List;

@Tag(name = "地区信息")
@RestController
@RequestMapping("/app/region")
public class RegionController {

    @Autowired
    ProvinceInfoService provinceInfoService;
    @Autowired
    CityInfoService cityInfoService;
    @Autowired
    DistrictInfoService districtInfoService;

    @Operation(summary="查询省份信息列表")
    @GetMapping("province/list")
    public Result<List<ProvinceInfo>> listProvince(){
        return Result.ok(provinceInfoService.list());
    }

    @Operation(summary="根据省份id查询城市信息列表")
    @GetMapping("city/listByProvinceId")
    public Result<List<CityInfo>> listCityInfoByProvinceId(@RequestParam Long id){
        LambdaQueryWrapper<CityInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CityInfo::getProvinceId,id);
        return Result.ok(cityInfoService.list(wrapper));
    }

    @GetMapping("district/listByCityId")
    @Operation(summary="根据城市id查询区县信息")
    public Result<List<DistrictInfo>> listDistrictInfoByCityId(@RequestParam Long id){
        LambdaQueryWrapper<DistrictInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DistrictInfo::getCityId,id);
        return Result.ok(districtInfoService.list(wrapper));
    }
}
