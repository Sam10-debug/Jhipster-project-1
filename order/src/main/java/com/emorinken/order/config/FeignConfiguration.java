package com.emorinken.order.config;

import com.emorinken.order.client.UserFeignClientInterceptor;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    @Bean
    feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.BASIC;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new UserFeignClientInterceptor();
    }
}
