package com.byb.userservice.Service.Impl;

import com.byb.security.Entity.SecurityUser;
import com.byb.userservice.Dao.PermissionDao;
import com.byb.userservice.Dao.UserAuthDao;
import com.byb.userservice.Dao.UserDao;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Service.UserService;
import com.byb.userservice.Vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> params = new HashMap<>();
        String[] temp = username.split("-");
        if(temp.length != 2){
            throw new UsernameNotFoundException("请求错误");
        }
        params.put("type", temp[1]);
        params.put("identifier", temp[0]);
        UserVo user = userAuthDao.selectOneVo(params);
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        SecurityUser securityUser = new SecurityUser();
        BeanUtils.copyProperties(user, securityUser);

        List<String> permissionList = permissionDao.selectByUser(securityUser.getUsername());
        securityUser.setPermissions(permissionList);
        return securityUser;
    }

    public static SecurityUser getCurrentUser(){
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
