package com.byb.systemservice.Controller;

import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.systemservice.Service.MessageService;
import com.byb.systemservice.Vo.MessageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/launchMessage")
    public Result<Map<String, Object>> launchMessage(@RequestBody MessageForm messageForm, HttpServletResponse response){
        if(messageForm.getContent() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "内容为空"));
        }

        if(messageForm.getSpecificUsers() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "未指定用户"));
        }



        return null;
    }

}
