package com.atguigu.shangTingApartment.web.admin.controller.apartment;


import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.model.entity.FeeKey;
import com.atguigu.shangTingApartment.model.entity.FeeValue;
import com.atguigu.shangTingApartment.web.admin.service.FeeKeyService;
import com.atguigu.shangTingApartment.web.admin.service.FeeValueService;
import com.atguigu.shangTingApartment.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "房间杂费管理")
@RestController
@RequestMapping("/admin/fee")
public class FeeController {

    @Autowired
    FeeKeyService feeKeyService;
    @Autowired
    FeeValueService feeValueService;

    @Operation(summary = "保存或更新杂费名称")
    @PostMapping("key/saveOrUpdate")
    public Result saveOrUpdateFeeKey(@RequestBody FeeKey feeKey) {
        boolean flag = feeKeyService.saveOrUpdate(feeKey);
        return flag?Result.ok():Result.fail();
    }

    @Operation(summary = "保存或更新杂费值")
    @PostMapping("value/saveOrUpdate")
    public Result saveOrUpdateFeeValue(@RequestBody FeeValue feeValue) {
        boolean flag = feeValueService.saveOrUpdate(feeValue);
        return flag?Result.ok():Result.fail();
    }


    @Operation(summary = "查询全部杂费名称和杂费值列表")
    @GetMapping("list")
    public Result<List<FeeKeyVo>> feeInfoList() {
        List<FeeKeyVo> list = feeKeyService.queryAll();
        return Result.ok(list);
    }

    @Operation(summary = "根据id删除杂费名称")
    @DeleteMapping("key/deleteById")
    public Result deleteFeeKeyById(@RequestParam Long feeKeyId) {
        //删除杂费值
        LambdaQueryWrapper<FeeValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FeeValue::getFeeKeyId,feeKeyId);
        feeValueService.remove(wrapper);
        //删除杂费名称
        boolean flag = feeKeyService.removeById(feeKeyId);
        return flag? Result.ok() : Result.fail();
    }

    @Operation(summary = "根据id删除杂费值")
    @DeleteMapping("value/deleteById")
    public Result deleteFeeValueById(@RequestParam Long id) {
        boolean flag = feeValueService.removeById(id);
        return flag?Result.ok():Result.fail();
    }
}
