package com.byb.systemservice.Listener;

import com.alibaba.fastjson.JSONObject;
import com.byb.BaseUtil.Config.ConstantConfig;
import com.byb.systemservice.Service.EmailService;
import com.byb.systemservice.Service.MessageService;
import com.byb.systemservice.Service.SyslogService;
import com.byb.systemservice.Vo.MessageForm;
import com.byb.systemservice.Vo.SyslogForm;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class Listener{

        @Autowired
        private SyslogService syslogService;

        @Autowired
        private MessageService messageService;

        @Autowired
        private EmailService emailService;

//        @RabbitListener(queues = "test")
//        public void rabbitmqTest(String msg){
//            System.out.println("rabbitmq:  " + msg);
//        }

        @RabbitListener(queues = ConstantConfig.SYSL0G_QUEUE)
        public void addSyslog(String syslogFomrStr){
            SyslogForm syslogForm = JSONObject.parseObject(syslogFomrStr, SyslogForm.class);
            if(syslogForm.getObjId()==null){
                System.err.println("OBJ_ID 为空");
                return;
            }
            Long logId = syslogService.addLog(syslogForm);
            if(logId.equals(-1l)){
                System.err.println("插入日志错误");
                return;
            }
        }

        @RabbitListener(queues = ConstantConfig.MESSAGE_QUEUE)
        public void launchMessage(String messageFormStr){
            MessageForm messageForm = JSONObject.parseObject(messageFormStr, MessageForm.class);

            if(messageForm.getContent() == null){
                System.err.println("消息内容为空");
                return;
            }

            if(messageForm.getScope() == null){
                System.err.println("未指定范围");
                return;
            }

            if(messageForm.getSpecificUsers() == null){
                System.err.println("未指定用户");
                return;
            }

            Map<String, Object> dataMap = messageService.launchMessage(messageForm);
            Boolean flag = (Boolean) dataMap.get("flag");
            if(!flag){
                System.err.println("消息插入数据库失败");
                return;
            }

            String content = messageForm.getContent();
            try {
                emailService.sendEmail(messageForm.getEmails(), content);
            }catch (Exception e){
                e.printStackTrace();
                return;
            }

        }
}
