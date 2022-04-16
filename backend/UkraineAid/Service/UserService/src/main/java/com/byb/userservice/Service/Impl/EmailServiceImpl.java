package com.byb.userservice.Service.Impl;

import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSONObject;
import com.byb.security.Security.TokenManager;
import com.byb.userservice.Service.EmailService;
import com.byb.userservice.Vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private MailSender sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TokenManager tokenManager;


    @Override
    public Boolean sendEmail(String to, String text) {
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

    @Override
    public Boolean sendHtml(UserForm userForm) {

        try {
            Map<String, String> params = new HashMap<>();
            String userinfo = JSONObject.toJSONString(userForm);
            String token = tokenManager.createToken(userinfo);
            params.put("token", token);
            File file = new File("E:\\ukraine-bhelper\\backend\\UkraineAid\\Service\\UserService\\src\\main\\resources\\static\\Active.html");
            String htmlStr = FileReader.create(file).readString();
            Set<String> stringSet = params.keySet();
            Iterator<String> iterator = stringSet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                htmlStr = htmlStr.replace("${" + key + "}", params.get(key));
            }
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(from);
            helper.setText(htmlStr, true);
            helper.setTo(userForm.getIdentifier());
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
