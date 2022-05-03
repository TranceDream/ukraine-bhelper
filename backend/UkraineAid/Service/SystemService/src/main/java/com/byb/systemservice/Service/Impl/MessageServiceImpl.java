package com.byb.systemservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.systemservice.Dao.MessageDao;
import com.byb.systemservice.Entity.Message;
import com.byb.systemservice.Service.MessageService;
import com.byb.systemservice.Vo.MessageForm;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {

    @Override
    public Map<String, Object> launchMessage(MessageForm messageForm) {
        Message message = new Message();
        Map<String, Object> result = new HashMap<>();
        try {
            BeanUtils.copyProperties(messageForm, message);
            baseMapper.insert(message);
            Long messageId = message.getMessageId();
            result.put("flag", true);
            result.put("messageId", messageId);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("flag", false);
        return result;
    }
}
