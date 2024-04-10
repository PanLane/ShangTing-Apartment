package com.atguigu.shangTingApartment.web.admin.controller.system;


import com.atguigu.shangTingApartment.common.exceptions.ApartmentCustomException;
import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.common.result.ResultCodeEnum;
import com.atguigu.shangTingApartment.common.utils.MD5Util;
import com.atguigu.shangTingApartment.model.entity.SystemUser;
import com.atguigu.shangTingApartment.model.entity.UserInfo;
import com.atguigu.shangTingApartment.model.enums.BaseStatus;
import com.atguigu.shangTingApartment.web.admin.service.SystemUserService;
import com.atguigu.shangTingApartment.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.shangTingApartment.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "后台用户信息管理")
@RestController
@RequestMapping("/admin/system/user")
public class SystemUserController {

    @Autowired
    SystemUserService systemUserService;

    @Operation(summary = "根据条件分页查询后台用户列表")
    @GetMapping("page")
    public Result<IPage<SystemUserItemVo>> page(@RequestParam long current, @RequestParam long size, SystemUserQueryVo queryVo) {
        Page<SystemUserItemVo> page = new Page<>(current,size);
        page = systemUserService.customPage(page,queryVo);
        return Result.ok(page);
    }

    @Operation(summary = "根据ID查询后台用户信息")
    @GetMapping("getById")
    public Result<SystemUserItemVo> getById(@RequestParam Long id) {
        return Result.ok(systemUserService.getDetailById(id));
    }

    @Operation(summary = "保存或更新后台用户信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody SystemUser systemUser) {
        //密码明文转密文
        systemUser.setPassword(MD5Util.encrypt(systemUser.getPassword()));
        return systemUserService.saveOrUpdate(systemUser) ? Result.ok() : Result.fail();
    }

    @Operation(summary = "判断后台用户名是否可用")
    @GetMapping("isUserNameAvailable")
    public Result<Boolean> isUsernameExists(@RequestParam String username) {
        //根据用户名查询用户信息
        LambdaQueryWrapper<SystemUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemUser::getUsername,username);
        //用户信息不存在返回ok，存在返回fail
        return systemUserService.getOne(wrapper) == null ? Result.ok(true) : Result.build(false,ResultCodeEnum.ADMIN_ACCOUNT_EXIST_ERROR);
    }

    @DeleteMapping("deleteById")
    @Operation(summary = "根据ID删除后台用户信息")
    public Result removeById(@RequestParam Long id) {
        return systemUserService.removeById(id) ? Result.ok() : Result.fail();
    }

    @Operation(summary = "根据ID修改后台用户状态")
    @PostMapping("updateStatusByUserId")
    public Result updateStatusByUserId(@RequestParam Long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<SystemUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SystemUser::getId,id).set(SystemUser::getStatus,status);
        return systemUserService.update(wrapper) ? Result.ok() : Result.fail();
    }
}
