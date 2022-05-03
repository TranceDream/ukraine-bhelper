package com.byb.systemservice.Service.Impl;

import com.byb.systemservice.Service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.userService.activeFilePath}")
    private String activeFilePath;

    @Resource
    private MailSender sender;

    @Override
    public Boolean sendEmail(String[] to, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom(from);
        try {
            sender.send(simpleMailMessage);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
