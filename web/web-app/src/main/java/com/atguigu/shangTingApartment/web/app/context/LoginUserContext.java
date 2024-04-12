package com.atguigu.shangTingApartment.web.app.context;

import com.atguigu.shangTingApartment.web.app.vo.user.UserInfoVo;

public class LoginUserContext {

    private static final ThreadLocal<UserInfoVo> threadLocal = new ThreadLocal<>();

    public static void set(UserInfoVo userInfoVo){
        threadLocal.set(userInfoVo);
    }

    public static UserInfoVo get(){
        return threadLocal.get();
    }

    public static void clear(){
        threadLocal.remove();
    }

}
