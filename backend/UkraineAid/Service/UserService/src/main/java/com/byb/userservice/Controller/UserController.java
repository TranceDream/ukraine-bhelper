package com.byb.userservice.Controller;

import com.alibaba.fastjson.JSONObject;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.BaseUtil.Utils.UUIDUtils;
import com.byb.security.Entity.SecurityUser;
import com.byb.security.Security.TokenManager;
import com.byb.userservice.Service.EmailService;
import com.byb.userservice.Service.Impl.UserDetailsServiceImpl;
import com.byb.userservice.Service.UserAuthService;
import com.byb.userservice.Service.UserService;
import com.byb.userservice.Vo.UserForm;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/test")
    public Result<Map<String, Object>> test(){
        Map<String, Object> map = new HashMap<>();
        map.put("user", "user");
        return new Result<>(map, Result.SUCCESS);
    }

    @PostMapping("/checkToken")
    public void checkToken(HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        map.put("result", "authentication success");
        ResponseUtil.out(response, new Result(map, Result.SUCCESS));
    }

    @PostMapping("/addUserByEmail")
    public Result<Map<String, Object>> addByEmail(@RequestBody UserForm userForm, HttpServletResponse response){
        if(userForm.getIdentifier()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "请填写邮箱地址"));
        }

        String credential = userForm.getCredential();
        if(credential==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "密码不能为空"));
        }

        if(credential.length()<8){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "密码长度至少8位"));
        }

        if(credential.length()>20){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "密码长度不超过20位"));
        }

        String letter = ".*[a-zA-Z]+.*";
        String digit = ".*[0-9]+.*";
        Matcher m = Pattern.compile(letter).matcher(credential);
        if(!m.matches()){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "密码需要包含字母"));
        }

        m =  Pattern.compile(digit).matcher(credential);
        if(!m.matches()){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "密码需要包含数字"));
        }

        if(userForm.getCountry()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "请填写您的国籍"));
        }

        if(userForm.getCity()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "请填写您的城市"));
        }

        Boolean isExist = userAuthService.checkAccount(userForm);
        if(isExist){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "用户已存在"));
        }

        String uuid = UUIDUtils.createUUID();
        redisTemplate.opsForValue().set(userForm.getIdentifier(), uuid,18000, TimeUnit.SECONDS);
        userForm.setUuid(uuid);
        Boolean isSend = emailService.sendHtml(userForm);
        if(!isSend){
            redisTemplate.opsForHash().delete(userForm.getIdentifier());
            ResponseUtil.out(response, new Result(null, Result.FAIL, "邮件发送失败，请检查邮箱格式，或稍后重试"));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("邮箱", userForm.getIdentifier());
        return new Result<>(map, Result.SUCCESS, "请前往邮箱激活账户");
    }

//    @PostMapping("/toActiveEmail")
//    public Result<Map<String, Object>> toActiveEmail(@RequestBody UserForm userForm, HttpServletResponse response, HttpServletRequest request){
//        String uid = request.getHeader("userId");
//        if(uid == null){
//            ResponseUtil.out(response, new Result(null, Result.FAIL, "请求失败，请稍后重试"));
//        }
//        Long userId = Long.valueOf(uid);
//        String uuid = UUIDUtils.createUUID();
//        redisTemplate.opsForValue().set(userId,uuid,1800, TimeUnit.SECONDS);
//        Boolean isSend = emailService.sendEmail(userForm.getIdentifier(), uuid);
//        if(!isSend){
//            redisTemplate.opsForHash().delete(userId);
//            ResponseUtil.out(response, new Result(null, Result.FAIL, "邮件发送失败，请稍后重试"));
//        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("邮箱", userForm.getIdentifier());
//        return new Result<>(map, Result.SUCCESS, "请前往邮箱激活账户");
//    }

    @GetMapping("/activeEmail")
    public Result<Map<String, Object>> activeEmail(@RequestParam("token") String token,  HttpServletResponse response, HttpServletRequest request){

        UserForm userForm = new UserForm();

        try {
            String userinfo = tokenManager.getUserInfoFromToken(token);
            userForm = JSONObject.parseObject(userinfo, UserForm.class);
        }catch (Exception e){
            e.printStackTrace();
            ResponseUtil.out(response, new Result(null, Result.FAIL, "凭证有误"));
        }

        if(userForm.getCountry()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "请填写您的国籍"));
        }

        if(userForm.getCity()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "请填写您的城市"));
        }

        String uuid = userForm.getUuid();
        String redisUUID = (String) redisTemplate.opsForValue().get(userForm.getIdentifier());
        if(redisUUID == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "连接超时，请重新注册"));
        }
        else if(!redisUUID.equals(uuid)){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "认证失败"));
        }
        else{
            Long userId = userService.createAccount(userForm);
            userForm.setUserId(userId);
            userAuthService.createAccount(userForm);
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            return new Result<>(map, Result.SUCCESS, "认证成功");
        }
        return null;
    }

    @PostMapping("/emailtest")
    public String emailtest(){
        emailService.sendEmail("1.com.com.com.com", "ddd");
        return "dddd";
    }

    @PostMapping("/htmlEmail")
    public String ttt(@RequestBody UserForm userForm) {
//        emailService.sendHtml(userForm.getIdentifier());
        return "ttt";
    }

}
