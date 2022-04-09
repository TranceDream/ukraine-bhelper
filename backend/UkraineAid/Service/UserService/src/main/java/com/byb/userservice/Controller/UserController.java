package com.byb.userservice.Controller;

import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.security.Entity.SecurityUser;
import com.byb.userservice.Service.Impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/test")
    public Result<Map<String, Object>> test(){
        SecurityUser user = UserDetailsServiceImpl.getCurrentUser();
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        return new Result<>(map, Result.SUCCESS);
    }

    @PostMapping("/hello")
    public Result<Map<String, Object>> hello(){
        Map<String, Object> map = new HashMap<>();
        map.put("user", "hello");
        return new Result<>(map, Result.SUCCESS);
    }

    @PostMapping("checkToken")
    public void checkToken(HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        map.put("result", "authentication success");
        ResponseUtil.out(response, new Result(map, Result.SUCCESS));
    }

    @Value("${pattern.dateformat}")
    private String dateformat;

    @GetMapping("now")
    public String now(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
    }

}
