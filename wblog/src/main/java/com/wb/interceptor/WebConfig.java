package com.wb.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 使用 -WebMvcConfigurerAdapter(已过时) WebMvcConfigurer(推荐) 可以来扩展 springMvc 的功能
 * 如果继承 WebMvcConfigurationSupport 后需要自己再重写相应的方法，
 * 否则自动配置的静态资源路径（classpath:/META/resources/，classpath:/resources/，classpath:/static/，classpath:/public/）不生效。
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 所有的 webMvcConfigurer 组件都会一起起作用
    @Bean // 将所有组件注册在容器中
    public WebMvcConfigurer webMvcConfigurer() {
        WebMvcConfigurer wmc = new WebMvcConfigurer() {

            /**
             * 将登录拦截器注册在组件中
             * @param registry
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginInterceptor())
                        .addPathPatterns("/admin/**")
                        // 排除以下访问路径
                        .excludePathPatterns("/admin", "/admin/login", "/","/static/**","/templates/**");
            }
        };
        return wmc;
    }


}
