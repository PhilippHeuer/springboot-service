package com.github.philippheuer.restapi.config;

import com.github.philippheuer.restapi.interceptor.LoggingRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestInterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoggingRequestInterceptor loggingRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingRequestInterceptor).addPathPatterns("/**/");
    }
}