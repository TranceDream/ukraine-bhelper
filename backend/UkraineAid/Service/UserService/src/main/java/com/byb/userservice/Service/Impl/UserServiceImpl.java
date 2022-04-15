package com.byb.userservice.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.userservice.Dao.UserAuthDao;
import com.byb.userservice.Dao.UserDao;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Entity.UserAuth;
import com.byb.userservice.Service.UserService;
import com.byb.userservice.Vo.UserForm;
import com.byb.userservice.Vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserAuthDao userAuthDao;

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
            return userId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
