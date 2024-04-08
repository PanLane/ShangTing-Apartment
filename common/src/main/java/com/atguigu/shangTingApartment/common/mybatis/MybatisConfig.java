package com.atguigu.shangTingApartment.common.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.atguigu.shangTingApartment.web.*.mapper")
public class MybatisConfig {
}
