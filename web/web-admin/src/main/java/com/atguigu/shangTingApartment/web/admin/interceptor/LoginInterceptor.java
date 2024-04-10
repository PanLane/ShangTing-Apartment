package com.atguigu.shangTingApartment.web.admin.interceptor;

import com.atguigu.shangTingApartment.common.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String access_token = request.getHeader("access_token");
        JwtUtil.parseToken(access_token);
        return true;
    }
}
