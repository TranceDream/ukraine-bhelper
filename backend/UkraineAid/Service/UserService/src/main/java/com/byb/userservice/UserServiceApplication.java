package com.byb.userservice;

import com.byb.openfeign.Client.SysClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(clients = SysClient.class)
@SpringBootApplication
@ComponentScan("com.byb")
@MapperScan("com.byb.userservice.Dao")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
