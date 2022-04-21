package com.byb.userservice.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.security.Security.DefaultPasswordEncoder;
import com.byb.userservice.Dao.RoleDao;
import com.byb.userservice.Dao.UserAuthDao;
import com.byb.userservice.Dao.UserDao;
import com.byb.userservice.Dao.UserRoleDao;
import com.byb.userservice.Entity.UserRole;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Service.UserService;
import com.byb.userservice.Vo.UserForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @PostConstruct
    private void loadDB(){
        NormalUserRoleId = roleDao.selectRoleIdByName(NormalUserRoleName);
    }

    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleDao roleDao;

    @Value("${spring.userService.normalUserRoleName}")
    private String NormalUserRoleName;

    private int NormalUserRoleId;

    @Override
    public User getById(Long id) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("USER_ID", id));
        return user;
    }

    @Override
    public Long createAccount(UserForm userForm) {
        try {
            User user = new User();
            BeanUtils.copyProperties(userForm, user);
            baseMapper.insert(user);
            Long userId = user.getUserId();
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(NormalUserRoleId);
            userRoleDao.insert(userRole);
            return userId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
