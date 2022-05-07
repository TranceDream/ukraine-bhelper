package com.byb.newsservice;

import com.byb.openfeign.Client.AuditClient;
import com.byb.openfeign.Client.ReportClient;
import com.byb.openfeign.Client.SysClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 22:51
 */

@SpringBootApplication
@ComponentScan("com.byb")
@MapperScan("com.byb.newsservice.Dao")
@EnableFeignClients(clients = {ReportClient.class, AuditClient.class})
public class NewsServiceApplication {

    public static void main(String[] args) {
            SpringApplication.run(NewsServiceApplication.class, args);
        }


}
