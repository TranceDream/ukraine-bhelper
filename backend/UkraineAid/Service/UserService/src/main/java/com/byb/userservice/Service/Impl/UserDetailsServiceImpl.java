package com.byb.userservice.Service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.byb.security.Entity.SecurityMenu;
import com.byb.security.Entity.SecurityUser;
import com.byb.userservice.Dao.PermissionDao;
import com.byb.userservice.Dao.UserAuthDao;
import com.byb.userservice.Dao.UserDao;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Service.UserService;
import com.byb.userservice.Vo.MenuVo;
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

import java.util.*;

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
        List<MenuVo> menuVos = userDao.selectMenuList(user.getUserId());
        Set<String> securityMenus = new HashSet<>();
        for(MenuVo menuVo : menuVos){
            SecurityMenu securityMenu = new SecurityMenu();
            if(menuVo!=null) {
                BeanUtils.copyProperties(menuVo, securityMenu);
                securityMenus.add(JSONObject.toJSONString(securityMenu));
            }
        }
        List<SecurityMenu> list = new ArrayList<>();
        for(String str : securityMenus){
            SecurityMenu securityMenu = new SecurityMenu();
            securityMenu = JSONObject.parseObject(str, SecurityMenu.class);
            list.add(securityMenu);
        }
        securityUser.setMenus(list);
        List<String> permissionList = permissionDao.selectByUser(securityUser.getUsername());
        securityUser.setPermissions(permissionList);
        return securityUser;
    }

}
