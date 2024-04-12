package com.atguigu.shangTingApartment.common.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AliyunSMSProperties.class)
public class AliyunSMSConfig {

    @Autowired
    AliyunSMSProperties aliyunSMSProperties;

    @Bean
    public Client smsClient(){
        Config config = new Config();
        config.setAccessKeyId(aliyunSMSProperties.getAccessKeyId()).setAccessKeySecret(aliyunSMSProperties.getAccessKeySecret())
                .setEndpoint(aliyunSMSProperties.getEndpoint());
        try {
            return new Client(config);
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

}
