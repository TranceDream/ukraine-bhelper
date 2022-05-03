package com.byb.systemservice.Listener;

import com.alibaba.fastjson.JSONObject;
import com.byb.BaseUtil.Config.ConstantConfig;
import com.byb.BaseUtil.Utils.Result;
import com.byb.systemservice.Service.SyslogService;
import com.byb.systemservice.Vo.SyslogForm;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Component
public class Listener{

        @Autowired
        private SyslogService syslogService;

//        @RabbitListener(queues = "test")
//        public void rabbitmqTest(String msg){
//            System.out.println("rabbitmq:  " + msg);
//        }

        @RabbitListener(queues = ConstantConfig.SYSL0G_QUEUE)
        public void addSyslog(String syslogFomrStr){
            SyslogForm syslogForm = JSONObject.parseObject(syslogFomrStr, SyslogForm.class);
            if(syslogForm.getObjId()==null){
                System.err.println("OBJ_ID 为空");
            }
            Long logId = syslogService.addLog(syslogForm);
            if(logId.equals(-1l)){
                System.err.println("插入日志错误");
            }
        }
}
