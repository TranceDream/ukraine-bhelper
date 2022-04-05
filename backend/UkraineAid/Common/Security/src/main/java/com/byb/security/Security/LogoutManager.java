package com.byb.security.Security;

import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutManager implements LogoutHandler {
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public LogoutManager(TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //1 从header里面获取token
        //2 token不为空，移除token，从redis删除token
        String token = request.getHeader("token");
        if(token != null) {
            //移除
            tokenManager.removeToken(token);
            //从token获取用户名
//            String username = tokenManager.getUserInfoFromToken(token).getUsername();
//            redisTemplate.delete(username);
        }
        ResponseUtil.out(response, new Result(Result.SUCCESS));
    }
}
