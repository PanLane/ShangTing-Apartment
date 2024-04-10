package com.atguigu.shangTingApartment.common.constant;

/**
 * 与使用Redis存储验证码相关的常量
 */
public class RedisConstant {
    //后台登录前缀
    public static final String ADMIN_LOGIN_PREFIX = "admin:login:";
    //后台登录的过期时间
    public static final Integer ADMIN_LOGIN_CAPTCHA_TTL_SES = 60*15;
    //APP登录前缀
    public static final String APP_LOGIN_PREFIX = "app:login:";
    //APP重发验证码间隔时间
    public static final Integer APP_LOGIN_RESENT_TIME_SEC = 60;
    //APP登录的过期时间
    public static final Integer APP_LOGIN_CAPTCHA_TTL_SES = 60 * 10;
}
