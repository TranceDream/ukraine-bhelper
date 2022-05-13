package com.byb.reportservice;

import com.byb.openfeign.Client.AuditClient;
import com.byb.openfeign.Client.HouseClient;
import com.byb.openfeign.Client.SysClient;
import com.byb.openfeign.Client.UserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(clients = {AuditClient.class, UserClient.class, SysClient.class, HouseClient.class})
public class ReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportServiceApplication.class, args);
    }

}
