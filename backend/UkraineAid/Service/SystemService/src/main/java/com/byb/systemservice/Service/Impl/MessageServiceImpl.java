package com.byb.systemservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.systemservice.Dao.MessageDao;
import com.byb.systemservice.Entity.Message;
import com.byb.systemservice.Service.MessageService;
import com.byb.systemservice.Vo.MessageForm;
import org.apache.tomcat.util.descriptor.web.MessageDestination;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {

    @Override
    public Map<String, Object> launchMessage(MessageForm messageForm) {
        return null;
    }
}
