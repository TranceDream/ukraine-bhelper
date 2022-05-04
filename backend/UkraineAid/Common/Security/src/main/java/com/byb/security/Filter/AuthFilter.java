package com.byb.security.Filter;

import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.security.Entity.SecurityUser;
import com.byb.security.Security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class AuthFilter extends BasicAuthenticationFilter {

    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private RedisTemplate redisTemplate;

    public AuthFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取当前认证成功用户权限信息
        try {
            UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
            if(authRequest != null) {
                SecurityContextHolder.getContext().setAuthentication(authRequest);
            }
            chain.doFilter(request,response);
        }catch (UsernameNotFoundException e){
            ResponseUtil.out(response, new Result(Result.NO_PERMISSION, "TOKEN ERROR"));
        }catch (AccessDeniedException e){
            ResponseUtil.out(response, new Result(Result.NO_PERMISSION, "YOU DON'T HAVE THIS AUTHORITY"));
        }catch (RuntimeException e){
            ResponseUtil.out(response, new Result(Result.NO_PERMISSION, "请重新登陆"));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        if(token != null) {
            SecurityUser securityUser = new SecurityUser();
            //从token获取用户名
            String username = tokenManager.getUserInfoFromToken(token);
            securityUser.setUserId(Long.valueOf(username));
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null) {
                securityUser = (SecurityUser) authentication.getPrincipal();
                //从redis获取对应权限列表
                if (!securityUser.getUserId().equals(Long.valueOf(username))) {
                    throw new UsernameNotFoundException("TOKEN WRONG");
                }
            }
            List<String> permissionValueList = (List<String>)redisTemplate.opsForValue().get(username);
            if(permissionValueList == null){
                throw new RuntimeException();
            }
            Collection<GrantedAuthority> authority = new ArrayList<>();
//            for(String permissionValue : permissionValueList) {
//                SimpleGrantedAuthority auth = new SimpleGrantedAuthority(permissionValue);
//                authority.add(auth);
//            }
            securityUser.setPermissions(permissionValueList);
            return new UsernamePasswordAuthenticationToken(securityUser,token,authority);
        }
        throw new UsernameNotFoundException("TOKEN NOT FOUND");
    }
}
