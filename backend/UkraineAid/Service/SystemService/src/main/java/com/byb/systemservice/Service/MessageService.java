package com.byb.systemservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.systemservice.Entity.Message;
import com.byb.systemservice.Vo.MessageForm;

import java.util.Map;

public interface MessageService extends IService<Message> {

    Map<String, Object> launchMessage(MessageForm messageForm);

}
