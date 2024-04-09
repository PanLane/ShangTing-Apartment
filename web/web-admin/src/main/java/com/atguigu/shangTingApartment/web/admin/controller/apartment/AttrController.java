package com.atguigu.shangTingApartment.web.admin.controller.apartment;


import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.model.entity.AttrKey;
import com.atguigu.shangTingApartment.model.entity.AttrValue;
import com.atguigu.shangTingApartment.web.admin.service.AttrKeyService;
import com.atguigu.shangTingApartment.web.admin.service.AttrValueService;
import com.atguigu.shangTingApartment.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "房间属性管理")
@RestController
@RequestMapping("/admin/attr")
public class AttrController {

    @Autowired
    AttrKeyService attrKeyService;
    @Autowired
    AttrValueService attrValueService;

    @Operation(summary = "新增或更新属性名称")
    @PostMapping("key/saveOrUpdate")
    public Result saveOrUpdateAttrKey(@RequestBody AttrKey attrKey) {
        boolean flag = attrKeyService.saveOrUpdate(attrKey);
        return flag?Result.ok():Result.fail();
    }

    @Operation(summary = "新增或更新属性值")
    @PostMapping("value/saveOrUpdate")
    public Result saveOrUpdateAttrValue(@RequestBody AttrValue attrValue) {
        boolean flag = attrValueService.saveOrUpdate(attrValue);
        return flag?Result.ok():Result.fail();
    }


    @Operation(summary = "查询全部属性名称和属性值列表")
    @GetMapping("list")
    public Result<List<AttrKeyVo>> listAttrInfo() {
        List<AttrKeyVo> list = attrKeyService.queryAll();
        return Result.ok(list);
    }

    @Operation(summary = "根据id删除属性名称")
    @DeleteMapping("key/deleteById")
    public Result removeAttrKeyById(@RequestParam Long attrKeyId) {
        //删除属性值
        LambdaQueryWrapper<AttrValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AttrValue::getAttrKeyId,attrKeyId);
        attrValueService.remove(wrapper);
        //删除属性名称
        boolean flag = attrKeyService.removeById(attrKeyId);
        return flag? Result.ok() : Result.fail();
    }

    @Operation(summary = "根据id删除属性值")
    @DeleteMapping("value/deleteById")
    public Result removeAttrValueById(@RequestParam Long id) {
        boolean flag = attrValueService.removeById(id);
        return flag?Result.ok():Result.fail();
    }

}
