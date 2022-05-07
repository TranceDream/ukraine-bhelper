package com.byb.houseservice;

import com.byb.openfeign.Client.ReportClient;
import com.byb.openfeign.Client.SysClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.byb")
@MapperScan("com.byb.houseservice.Dao")
@EnableFeignClients(clients = ReportClient.class)
public class HouseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseServiceApplication.class, args);
    }

}
