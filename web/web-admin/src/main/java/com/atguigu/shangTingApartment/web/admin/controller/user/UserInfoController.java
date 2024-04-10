package com.atguigu.shangTingApartment.web.admin.controller.user;


import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.model.entity.UserInfo;
import com.atguigu.shangTingApartment.model.enums.BaseStatus;
import com.atguigu.shangTingApartment.web.admin.service.UserInfoService;
import com.atguigu.shangTingApartment.web.admin.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "用户信息管理")
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @Operation(summary = "分页查询用户信息")
    @GetMapping("page")
    public Result<IPage<UserInfo>> pageUserInfo(@RequestParam long current, @RequestParam long size, UserInfoQueryVo queryVo) {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        String phone = queryVo.getPhone();
        BaseStatus status = queryVo.getStatus();
        wrapper.like(phone!=null && phone.length()>0,UserInfo::getPhone,phone);
        wrapper.eq(status!=null,UserInfo::getStatus,status);
        return Result.ok(userInfoService.page(new Page<>(current,size),wrapper));
    }

    @Operation(summary = "根据用户id更新账号状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<UserInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserInfo::getId,id).set(UserInfo::getStatus,status);
        return userInfoService.update(wrapper) ? Result.ok() : Result.fail();
    }
}
