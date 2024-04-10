package com.atguigu.shangTingApartment.web.admin.controller.lease;


import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.model.entity.LeaseAgreement;
import com.atguigu.shangTingApartment.model.enums.LeaseStatus;
import com.atguigu.shangTingApartment.web.admin.service.LeaseAgreementService;
import com.atguigu.shangTingApartment.web.admin.vo.agreement.AgreementQueryVo;
import com.atguigu.shangTingApartment.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "租约管理")
@RestController
@RequestMapping("/admin/agreement")
public class LeaseAgreementController {

    @Autowired
    LeaseAgreementService leaseAgreementService;

    @Operation(summary = "保存或修改租约信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody LeaseAgreement leaseAgreement) {
        return leaseAgreementService.saveOrUpdate(leaseAgreement) ? Result.ok() : Result.fail();
    }

    @Operation(summary = "根据条件分页查询租约列表")
    @GetMapping("page")
    public Result<IPage<AgreementVo>> page(@RequestParam long current, @RequestParam long size, AgreementQueryVo queryVo) {
        Page<AgreementVo> page = new Page<>(current,size);
        page = leaseAgreementService.customPage(page,queryVo);
        return Result.ok(page);
    }

    @Operation(summary = "根据id查询租约信息")
    @GetMapping(name = "getById")
    public Result<AgreementVo> getById(@RequestParam Long id) {
        AgreementVo agreementVo = leaseAgreementService.getDetailById(id);
        return Result.ok(agreementVo);
    }

    @Operation(summary = "根据id删除租约信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        return leaseAgreementService.removeById(id) ? Result.ok() : Result.fail();
    }

    @Operation(summary = "根据id更新租约状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam LeaseStatus status) {
        LambdaUpdateWrapper<LeaseAgreement> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(LeaseAgreement::getStatus,status).eq(LeaseAgreement::getId,id);
        return leaseAgreementService.update(wrapper) ? Result.ok() : Result.fail();
    }

}

