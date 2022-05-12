package com.byb.houseservice.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/11 22:16
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /upload/**为前端URL访问路径 后面为本地磁盘映射
        // System.getProperty("user.dir")为当前项目所在路径
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" +System.getProperty("user.dir")+ File.separator+"upload"+File.separator);
    }

}
