package com.atguigu.shangTingApartment.web.admin.controller.login;


import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.web.admin.context.SystemUserInfoVoContext;
import com.atguigu.shangTingApartment.web.admin.service.LoginService;
import com.atguigu.shangTingApartment.web.admin.vo.login.CaptchaVo;
import com.atguigu.shangTingApartment.web.admin.vo.login.LoginVo;
import com.atguigu.shangTingApartment.web.admin.vo.system.user.SystemUserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "后台管理系统登录管理")
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Operation(summary = "获取图形验证码")
    @GetMapping("login/captcha")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo captchaVo = loginService.getCaptcha();
        return Result.ok(captchaVo);
    }

    @Operation(summary = "登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        return Result.ok(loginService.login(loginVo));
    }

    @Operation(summary = "获取登陆用户个人信息")
    @GetMapping("info")
    public Result<SystemUserInfoVo> info() {
        return Result.ok(SystemUserInfoVoContext.get());
    }
}