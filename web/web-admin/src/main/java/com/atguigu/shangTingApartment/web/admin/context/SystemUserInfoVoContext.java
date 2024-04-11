package com.atguigu.shangTingApartment.web.admin.context;

import com.atguigu.shangTingApartment.web.admin.vo.system.user.SystemUserInfoVo;

public class SystemUserInfoVoContext {

    private static final ThreadLocal<SystemUserInfoVo> threadLocal = new ThreadLocal<>();

    public static void set(SystemUserInfoVo systemUserInfoVo){
        threadLocal.set(systemUserInfoVo);
    }

    public static SystemUserInfoVo get(){
        return threadLocal.get();
    }

    public static void clear(){
        threadLocal.remove();
    }

}
