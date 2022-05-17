package com.byb.userservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.BaseUtil.Utils.UUIDUtils;
import com.byb.security.Security.DefaultPasswordEncoder;
import com.byb.userservice.Dao.UserAuthDao;
import com.byb.userservice.Entity.UserAuth;
import com.byb.userservice.Service.UserAuthService;
import com.byb.userservice.Vo.UserForm;
import com.byb.userservice.Vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuth> implements UserAuthService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DefaultPasswordEncoder defaultPasswordEncoder;

    @Override
    public String createAccount(UserForm userForm) {
        try {
            UserAuth userAuth = new UserAuth();
            BeanUtils.copyProperties(userForm, userAuth);
            int identityType = baseMapper.selectIdentityType("EMAIL");
            userAuth.setIdentityType(identityType);
            String encode = defaultPasswordEncoder.encode(userForm.getCredential());
            userAuth.setCredential(encode);
            this.save(userAuth);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean checkAccount(UserForm userForm) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", baseMapper.selectIdentityType("EMAIL"));
        params.put("identifier", userForm.getIdentifier());
        UserVo userVo = baseMapper.selectOneVo(params);
        if(userVo!=null){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updatePwd(UserForm userForm) {
        String encode = defaultPasswordEncoder.encode(userForm.getCredential());
        Map<String, Object> params = new HashMap<>();
        params.put("pwd", encode);
        params.put("userId", userForm.getUserId());
        try {
            Integer update = baseMapper.updatePwd(params);
            if (update != 1) {
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
