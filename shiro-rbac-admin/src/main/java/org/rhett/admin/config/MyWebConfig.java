package org.rhett.admin.config;

import org.rhett.admin.model.constant.SysConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/shiro-rbac-admin/");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META_INF/resources/webjars/springfox-swagger-ui")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("redirect:/swagger-ui/index.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许跨域访问的路径
                //.allowedOrigins("*")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                // 暴露header中的其他属性给客户端应用程序
                // 如果不设置这个属性前端无法通过response header获取到Authorization也就是token
                .exposedHeaders(SysConstant.TOKEN_HEAD)
                .allowCredentials(true)
                //预检间隔时间
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
