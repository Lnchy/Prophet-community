package com.nf147.prophet.springConfig;

import com.nf147.prophet.util.validate.LoginInteceptor;
import com.nf147.prophet.util.validate.PowerInteceptor;
import com.nf147.prophet.util.validate.RoleInteceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurationSupport {

    @Bean
    public LoginInteceptor loginInteceptor() {
        return new LoginInteceptor();
    }

    @Bean
    public RoleInteceptor roleInteceptor() {
        return new RoleInteceptor();
    }

    @Bean
    public PowerInteceptor powerInteceptor() {
        return new PowerInteceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 验证登录拦截器
        registry.addInterceptor(loginInteceptor()).addPathPatterns("/**");

        // 角色控制拦截器
        registry.addInterceptor(roleInteceptor()).addPathPatterns("/**");

        // 权限控制拦截器
        registry.addInterceptor(powerInteceptor()).addPathPatterns("/**");

        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //静态资源地址
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
