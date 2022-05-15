package com.byb.newsservice.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/14 13:48
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //上传的图片在D盘下的image目录下，访问路径如：http://localhost:8082/image/100942.jpg
        //其中image表示访问的前缀。"file:D:/image/"是文件真实的存储路径
//        registry.addResourceHandler("/image/**").addResourceLocations("file:/Ukother/Ukpic/NewPic/");
//        registry.addResourceHandler("/log/**").addResourceLocations("file:/Ukother/Uklog");
    }
}

