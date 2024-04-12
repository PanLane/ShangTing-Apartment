package com.atguigu.shangTingApartment.web.app.service;


import com.atguigu.shangTingApartment.web.app.vo.user.LoginVo;

public interface LoginService {

    /**
     * 获取短信验证码
     * @return
     */
    void getCode(String phone);

    /**
     * 登录
     * @param loginVo
     */
    String login(LoginVo loginVo);
}
