package com.atguigu.shangTingApartment.web.app.service.impl;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.atguigu.shangTingApartment.common.constant.RedisConstant;
import com.atguigu.shangTingApartment.common.exceptions.ApartmentCustomException;
import com.atguigu.shangTingApartment.common.result.ResultCodeEnum;
import com.atguigu.shangTingApartment.common.utils.JwtUtil;
import com.atguigu.shangTingApartment.model.entity.UserInfo;
import com.atguigu.shangTingApartment.model.enums.BaseStatus;
import com.atguigu.shangTingApartment.web.app.mapper.UserInfoMapper;
import com.atguigu.shangTingApartment.web.app.service.LoginService;
import com.atguigu.shangTingApartment.web.app.vo.user.LoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    Client client;

    @Override
    public void getCode(String phone) {

        String key = RedisConstant.APP_LOGIN_PREFIX + phone;

        //判断是否存在key
        if(redisTemplate.hasKey(key)){
            //判断据上次发送验证码间隔是否达间隔时间
            if(RedisConstant.APP_LOGIN_CAPTCHA_TTL_SES - redisTemplate.getExpire(key) < RedisConstant.APP_LOGIN_RESENT_TIME_SEC){
                throw new ApartmentCustomException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }

        //生成四位数的随机验证码
        Random random = new Random();
        String code = random.nextInt(1000, 9999)+"";

        //将验证码存储到redis
        redisTemplate.opsForValue().set(key,code,RedisConstant.APP_LOGIN_CAPTCHA_TTL_SES, TimeUnit.SECONDS);

        //发送验证码给用户
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(phone);
        sendSmsRequest.setSignName("阿里云短信测试");
        sendSmsRequest.setTemplateCode("SMS_154950909");
        sendSmsRequest.setTemplateParam("""
                {"code":"%s"}
                """.formatted(code
        ));
        try {
            client.sendSms(sendSmsRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String login(LoginVo loginVo) {
        //获取手机号
        String phone = loginVo.getPhone();
        //手机号判空
        if(phone == null || phone.length()<=0) throw new ApartmentCustomException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        //获取验证码
        String code = loginVo.getCode();
        //验证码判空
        if(code == null || code.length()<=0) throw new ApartmentCustomException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        //生成key
        String key = RedisConstant.APP_LOGIN_PREFIX+phone;
        //判断验证码是否过期
        if (!redisTemplate.hasKey(key)) throw new ApartmentCustomException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        //判断验证码是否正确
        if(!code.equals(redisTemplate.opsForValue().get(key))) throw new ApartmentCustomException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        //校验完成，判断用户是否存在
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<UserInfo> eq = wrapper.eq(UserInfo::getPhone, phone);
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        //用户不存在，创建用户
        if(userInfo == null){
            UserInfo newUserInfo = new UserInfo();
            newUserInfo.setNickname("佚名"+phone.substring(5));
            newUserInfo.setPhone(phone);
            newUserInfo.setStatus(BaseStatus.ENABLE);
            userInfoMapper.insert(newUserInfo);
            userInfo = newUserInfo;
        }
        //判断用户是否被禁用
        if(userInfo.getStatus() == BaseStatus.DISABLE) throw new ApartmentCustomException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        //登录完成，生成token并响应
        return JwtUtil.createToken(userInfo.getId()+"", userInfo.getPhone(), userInfo.getAvatarUrl());
    }
}
