package com.byb.security.Security;

import com.byb.BaseUtil.Config.ConstantConfig;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogoutHandle implements LogoutHandler {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public LogoutHandle(TokenManager tokenManager,RedisTemplate redisTemplate) {
            this.tokenManager = tokenManager;
            this.redisTemplate = redisTemplate;
        }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(ConstantConfig.TOKEN_HEADER);
        if(token != null) {
            tokenManager.removeToken(token);
            String username = tokenManager.getUserInfoFromToken(token);
            redisTemplate.delete(username);
        }
        ResponseUtil.out(response, new Result(null, Result.SUCCESS, "登出成功"));
    }
}
