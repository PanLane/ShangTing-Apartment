package com.atguigu.shangTingApartment.web.admin.config;

import com.atguigu.shangTingApartment.web.admin.converter.StringToItemTypeConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringmvcConfig implements WebMvcConfigurer {
    @Autowired
    StringToItemTypeConvert stringToItemTypeConvert;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToItemTypeConvert);
    }
}
