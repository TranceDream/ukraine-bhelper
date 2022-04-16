package com.byb.security.Config;

import com.byb.security.Filter.AuthFilter;
import com.byb.security.Filter.LoginFilter;
import com.byb.security.Security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private RedisTemplate redisTemplate;
    private TokenManager tokenManager;
    private UserDetailsService userDetailsService;
    private DefaultPasswordEncoder defaultPasswordEncoder;
    private MyAuthenticProvider myAuthenticProvider;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager, RedisTemplate redisTemplate, MyAuthenticProvider myAuthenticProvider) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.myAuthenticProvider = myAuthenticProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnAuthHandle())//没有权限访问
                .accessDeniedHandler(new AuthDeniedHandle())
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .access("@authCheckService.checkPermission(request, authentication)")
                .and().logout().logoutUrl("/admin/acl/index/logout")//退出路径
                .addLogoutHandler(new LogoutManager(tokenManager,redisTemplate)).and()
                .addFilter(new LoginFilter(authenticationManager(), tokenManager, redisTemplate))
                .addFilter(new AuthFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic()
                .and().cors();
    }

    //调用userDetailsService和密码处理
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
        auth.authenticationProvider(myAuthenticProvider);
    }

    //不进行认证的路径，可以直接访问
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user/addUserByEmail","/user/activeEmail","/user/htmlEmail");
    }


}
