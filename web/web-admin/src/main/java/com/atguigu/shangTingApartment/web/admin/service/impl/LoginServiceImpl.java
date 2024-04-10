package com.atguigu.shangTingApartment.web.admin.service.impl;

import com.atguigu.shangTingApartment.common.constant.RedisConstant;
import com.atguigu.shangTingApartment.common.exceptions.ApartmentCustomException;
import com.atguigu.shangTingApartment.common.result.ResultCodeEnum;
import com.atguigu.shangTingApartment.common.utils.JwtUtil;
import com.atguigu.shangTingApartment.common.utils.MD5Util;
import com.atguigu.shangTingApartment.model.entity.SystemUser;
import com.atguigu.shangTingApartment.model.enums.BaseStatus;
import com.atguigu.shangTingApartment.web.admin.mapper.SystemUserMapper;
import com.atguigu.shangTingApartment.web.admin.service.LoginService;
import com.atguigu.shangTingApartment.web.admin.vo.login.CaptchaVo;
import com.atguigu.shangTingApartment.web.admin.vo.login.LoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SystemUserMapper systemUserMapper;

    @Override
    public CaptchaVo getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130,48,5);
        String verCode = specCaptcha.text().toLowerCase();//验证码
        String key = RedisConstant.ADMIN_LOGIN_PREFIX+ UUID.randomUUID();//key
        //将验证码和key存在到redis中
        redisTemplate.opsForValue().set(key,verCode,RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SES, TimeUnit.SECONDS);
        //封装数据并返回
        return new CaptchaVo(specCaptcha.toBase64(),key);
    }

    @Override
    public String login(LoginVo loginVo) {
        //判断用户输入的验证码是否为空
        if(StringUtils.isBlank(loginVo.getCaptchaCode())) throw new ApartmentCustomException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        //根据key获取value
        Object value = redisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        //判断验证码key是否过期
        if(value == null) throw new ApartmentCustomException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        //判断用户输入的验证码是否正确
        if(!loginVo.getCaptchaCode().equals(value)) throw new ApartmentCustomException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        //根据用户名查找用户信息
        LambdaQueryWrapper<SystemUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemUser::getUsername,loginVo.getUsername());
        SystemUser systemUser = systemUserMapper.selectOne(wrapper);
        //判断账号是否存在
        if(systemUser == null) throw new ApartmentCustomException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        //判断账号是否被禁用
        if(systemUser.getStatus() == BaseStatus.DISABLE) throw new ApartmentCustomException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        //判断密码是否正确
        if(!systemUser.getPassword().equals(MD5Util.encrypt(loginVo.getPassword()))) throw new ApartmentCustomException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        //登录成功
        //生成并返回token
        return JwtUtil.createToken(systemUser.getId(), systemUser.getUsername());
    }
}
