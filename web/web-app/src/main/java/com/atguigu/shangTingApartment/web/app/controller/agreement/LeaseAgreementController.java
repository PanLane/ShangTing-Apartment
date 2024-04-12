package com.atguigu.shangTingApartment.web.app.controller.agreement;


import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.model.entity.LeaseAgreement;
import com.atguigu.shangTingApartment.model.enums.LeaseStatus;
import com.atguigu.shangTingApartment.web.app.context.LoginUserContext;
import com.atguigu.shangTingApartment.web.app.service.LeaseAgreementService;
import com.atguigu.shangTingApartment.web.app.vo.agreement.AgreementDetailVo;
import com.atguigu.shangTingApartment.web.app.vo.agreement.AgreementItemVo;
import com.atguigu.shangTingApartment.web.app.vo.user.UserInfoVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/agreement")
@Tag(name = "租约信息")
public class LeaseAgreementController {

    @Autowired
    LeaseAgreementService leaseAgreementService;

    @Operation(summary = "获取个人租约基本信息列表")
    @GetMapping("listItem")
    public Result<List<AgreementItemVo>> listItem() {
        //获取已登录用户的信息
        UserInfoVo userInfoVo = LoginUserContext.get();
        //根据手机号查找个人租约基本信息列表
        List<AgreementItemVo> list = leaseAgreementService.listItem(userInfoVo.getNickname());//因为app使用手机号登录，因此用户名为手机号
        return Result.ok(list);
    }

    @Operation(summary = "根据id获取租约详细信息")
    @GetMapping("getDetailById")
    public Result<AgreementDetailVo> getDetailById(@RequestParam Long id) {
        AgreementDetailVo detailVo = leaseAgreementService.getDetailById(id);
        return Result.ok(detailVo);
    }

    @Operation(summary = "根据id更新租约状态", description = "用于确认租约和提前退租")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam LeaseStatus leaseStatus) {
        LambdaUpdateWrapper<LeaseAgreement> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(LeaseAgreement::getId,id).set(LeaseAgreement::getStatus,leaseStatus);
        return leaseAgreementService.update(wrapper) ? Result.ok() : Result.fail();
    }

    @Operation(summary = "保存或更新租约", description = "用于续约")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody LeaseAgreement leaseAgreement) {
        return leaseAgreementService.saveOrUpdate(leaseAgreement) ? Result.ok() : Result.fail();
    }
}