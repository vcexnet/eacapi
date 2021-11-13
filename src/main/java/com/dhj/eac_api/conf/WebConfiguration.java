package com.dhj.eac_api.conf;

import com.dhj.eac_api.interceptor.WebOrApiOpenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: WebConfiguration
 * @author: dhj
 * @date: 2021/11/4 20:11
 * @version: v1.0
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private WebOrApiOpenInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/error/**", "/css/**", "/js/**", "/images/**");
    }
}
