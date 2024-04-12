package com.atguigu.shangTingApartment.web.app.interceptors;

import com.atguigu.shangTingApartment.common.utils.JwtUtil;
import com.atguigu.shangTingApartment.web.app.context.LoginUserContext;
import com.atguigu.shangTingApartment.web.app.vo.user.UserInfoVo;
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
        //判断token是否合法
        Claims claims = JwtUtil.parseToken(request.getHeader("access_token"));
        //生成全局用户登录信息上下文
        String id = (String) claims.get("userId");
        String phone = (String) claims.get("username");
        String avatarUrl = (String) claims.get("avatarUrl");
        LoginUserContext.set(new UserInfoVo(id,phone,avatarUrl));
        //放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //清除登录信息
        LoginUserContext.clear();
    }
}
