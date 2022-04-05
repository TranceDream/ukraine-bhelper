package com.byb.userservice.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.userservice.Dao.UserDao;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public User getById(Long id) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("USER_ID", id));
        return user;
    }
}
