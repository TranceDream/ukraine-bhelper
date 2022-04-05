package com.byb.security.Security;

import com.byb.security.Entity.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MyAuthenticProvider implements AuthenticationProvider {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //1、获取表单输入的用户名
        String username = String.valueOf(authentication.getPrincipal());
        //2、获取表单输入的密码
        String password = (String) authentication.getCredentials();
        //3、查询用户是否存在
        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(username);
        if (securityUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, securityUser.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
        String enabled = "0";
        //查询用户角色
        Set<GrantedAuthority> authorities = new HashSet<>();
//        List<SysRole> sysRoles = sysUserRoleDao.selectSysRoleByUserId(securityUser.getUserId());
//        for (SysRole sysRole : sysRoles) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRole.getName()));
//        }
//        userEntity.setAuthorities(authorities);
        //进行登录
        return new UsernamePasswordAuthenticationToken(securityUser, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
