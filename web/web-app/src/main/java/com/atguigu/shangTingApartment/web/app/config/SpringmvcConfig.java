package com.atguigu.shangTingApartment.web.app.config;

import com.atguigu.shangTingApartment.web.app.converter.StringToBaseEnumConvertFactory;
import com.atguigu.shangTingApartment.web.app.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringmvcConfig implements WebMvcConfigurer {

    //@Autowired
    //StringToItemTypeConvert stringToItemTypeConvert;
    @Autowired
    LoginInterceptor loginInterceptor;
    @Value("${admin.author.path-patterns.include}")
    private String pathPattern;
    @Value("${admin.author.path-patterns.exclude}")
    private String excludePathPattern;
    @Autowired
    StringToBaseEnumConvertFactory stringToBaseEnumConvertFactory;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //registry.addConverter(stringToItemTypeConvert);
        registry.addConverterFactory(stringToBaseEnumConvertFactory);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns(pathPattern).excludePathPatterns(excludePathPattern);
    }
}
