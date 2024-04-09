package com.atguigu.shangTingApartment.web.admin.controller.apartment;


import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.model.entity.LeaseTerm;
import com.atguigu.shangTingApartment.web.admin.service.LeaseTermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "租期管理")
@RequestMapping("/admin/term")
@RestController
public class LeaseTermController {

    @Autowired
    LeaseTermService leaseTermService;

    @GetMapping("list")
    @Operation(summary = "查询全部租期列表")
    public Result<List<LeaseTerm>> listLeaseTerm() {
        List<LeaseTerm> list = leaseTermService.list();
        return Result.ok(list);
    }

    @PostMapping("saveOrUpdate")
    @Operation(summary = "保存或更新租期信息")
    public Result saveOrUpdate(@RequestBody LeaseTerm leaseTerm) {
        boolean flag = leaseTermService.saveOrUpdate(leaseTerm);
        if(flag) return Result.ok();
        else return Result.fail();
    }

    @DeleteMapping("deleteById")
    @Operation(summary = "根据ID删除租期")
    public Result deleteLeaseTermById(@RequestParam Long id) {
        boolean flag = leaseTermService.removeById(id);
        if(flag) return Result.ok();
        else return Result.fail();
    }
}
