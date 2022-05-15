package com.byb.PicService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/15 18:42
 */
@SpringBootApplication
@ComponentScan("com.byb")
public class PicServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicServiceApplication.class, args);
    }
}
