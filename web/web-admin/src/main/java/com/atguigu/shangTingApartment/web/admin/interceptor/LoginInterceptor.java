package com.atguigu.shangTingApartment.web.admin.interceptor;

import com.atguigu.shangTingApartment.common.utils.JwtUtil;
import com.atguigu.shangTingApartment.web.admin.context.SystemUserInfoVoContext;
import com.atguigu.shangTingApartment.web.admin.vo.system.user.SystemUserInfoVo;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String access_token = request.getHeader("access_token");
        Claims claims = JwtUtil.parseToken(access_token);
        String username = (String) claims.get("username");
        String avatarUrl = (String) claims.get("avatarUrl");
        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setName(username);
        systemUserInfoVo.setAvatarUrl(avatarUrl);
        SystemUserInfoVoContext.set(systemUserInfoVo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SystemUserInfoVoContext.clear();
    }
}
