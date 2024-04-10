package com.atguigu.shangTingApartment.web.admin.service;

import com.atguigu.shangTingApartment.web.admin.vo.login.CaptchaVo;
import com.atguigu.shangTingApartment.web.admin.vo.login.LoginVo;

public interface LoginService {

    /**
     * 获取图形验证码
     * @return
     */
    CaptchaVo getCaptcha();

    /**
     * 用户登录
     * @param loginVo
     */
    String login(LoginVo loginVo);
}
