package com.atguigu.shangTingApartment.web.admin.controller.apartment;


import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.model.entity.FacilityInfo;
import com.atguigu.shangTingApartment.model.enums.ItemType;
import com.atguigu.shangTingApartment.web.admin.service.FacilityInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "配套管理")
@RestController
@RequestMapping("/admin/facility")
public class FacilityController {

    @Autowired
    FacilityInfoService facilityInfoService;

    @Operation(summary = "[根据类型]查询配套信息列表")
    @GetMapping("list")
    public Result<List<FacilityInfo>> listFacility(@RequestParam(required = false) ItemType type) {
        LambdaQueryWrapper<FacilityInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(type!=null,FacilityInfo::getType,type);
        List<FacilityInfo> list = facilityInfoService.list(wrapper);
        return Result.ok(list);
    }

    @Operation(summary = "新增或修改配套信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody FacilityInfo facilityInfo) {
        boolean flag = facilityInfoService.saveOrUpdate(facilityInfo);
        if(flag) return Result.ok();
        else return Result.fail();
    }

    @Operation(summary = "根据id删除配套信息")
    @DeleteMapping("deleteById")
    public Result removeFacilityById(@RequestParam Long id) {
        boolean flag = facilityInfoService.removeById(id);
        if(flag) return Result.ok();
        else return Result.fail();
    }

}
