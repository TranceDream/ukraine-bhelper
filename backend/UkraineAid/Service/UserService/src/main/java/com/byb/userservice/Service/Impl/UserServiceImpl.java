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
import com.byb.userservice.Vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> selectUserList(UserForm userForm) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", (userForm.getPageNo()-1) * userForm.getPageSize());
        params.put("size", userForm.getPageSize());
        params.put("userId", userForm.getUserId());
        params.put("roleId", userForm.getRoleId());
        params.put("lockedMark", userForm.getLockedMark());
        params.put("orderText", userForm.getOrderText());
        params.put("country", userForm.getCountry());
        params.put("city", userForm.getCity());
        params.put("defaultRole", NormalUserRoleId);

        Map<String, Object> result = new HashMap<>();
        result.put("pageNo", userForm.getPageNo());
        result.put("pageSize", userForm.getPageSize());

        int total = baseMapper.countUserList(params);
        if(total>0){
            List<UserVo> dataList = baseMapper.selectUserList(params);
            result.put("data", dataList);
            result.put("total", total);
        }
        else{
            result.put("total", 0);
            result.put("data", null);
        }
        return result;
    }

    @Override
    public Map<String, Object> getUserDetail(UserForm userForm) {
        UserVo userVo = baseMapper.selectUserDetail(userForm.getUserId());
        Map<String, Object> result = new HashMap<>();
        result.put("data", userVo);
        return result;
    }
}
