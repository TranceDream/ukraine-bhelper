package com.byb.userservice.Config;

import com.byb.BaseUtil.Config.ConstantConfig;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue SysLog(){
        return new Queue(ConstantConfig.SYSL0G_QUEUE);
    }

}
