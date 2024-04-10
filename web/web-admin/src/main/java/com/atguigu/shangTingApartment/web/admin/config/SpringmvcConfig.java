package com.atguigu.shangTingApartment.web.admin.config;

import com.atguigu.shangTingApartment.web.admin.converter.StringToBaseEnumConvertFactory;
import com.atguigu.shangTingApartment.web.admin.converter.StringToItemTypeConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringmvcConfig implements WebMvcConfigurer {
    @Autowired
    private HandlerInterceptor handlerInterceptor;
    @Value("${admin.author.path-patterns.include}")
    private String pathPattern;
    @Value("${admin.author.path-patterns.exclude}")
    private String excludePathPattern;

    //@Autowired
    //StringToItemTypeConvert stringToItemTypeConvert;
    @Autowired
    StringToBaseEnumConvertFactory stringToBaseEnumConvertFactory;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //registry.addConverter(stringToItemTypeConvert);
        registry.addConverterFactory(stringToBaseEnumConvertFactory);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor).addPathPatterns(pathPattern).excludePathPatterns(excludePathPattern);
    }
}
