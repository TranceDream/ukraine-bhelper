package com.byb.userservice.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.security.Security.DefaultPasswordEncoder;
import com.byb.userservice.Dao.RoleDao;
import com.byb.userservice.Dao.UserAuthDao;
import com.byb.userservice.Dao.UserDao;
import com.byb.userservice.Dao.UserRoleDao;
import com.byb.userservice.Entity.RolePermission;
import com.byb.userservice.Entity.UserRole;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Service.UserService;
import com.byb.userservice.Vo.UserForm;
import com.byb.userservice.Vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate redisTemplate;

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
        params.put("name", userForm.getName());
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

    @Override
    public Map<String, Object> manageRole(UserForm userForm) {

        Long id = Long.valueOf(userForm.getUserRoleId());

        Map<String, Object> params = new HashMap<>();
        params.put("userRoleId", id);
        params.put("lockMark", userForm.getLockedMark());

        Map<String, Object> result = new HashMap<>();
        int update = userRoleDao.updateLockedMark(params);
        if(update!=1){
            result.put("flag", false);
            result.put("msg", "操作失败");
            return result;
        }
        UserRole userRole = userRoleDao.selectById(id);
        params.put("userId", userRole.getUserId());
        params.put("roleId", userRole.getRoleId());
        List<String> permissions = userRoleDao.selectUrlByRoleId(params);
        List<String> originPermissions = (List<String>) redisTemplate.opsForValue().get(String.valueOf(userForm.getUserId()));
        try {
            if(userForm.getLockedMark().equals("NO")){
                originPermissions.addAll(permissions);
                redisTemplate.opsForValue().set(String.valueOf(userForm.getUserId()), originPermissions);
            }else {
                originPermissions.removeAll(permissions);
                redisTemplate.opsForValue().set(String.valueOf(userForm.getUserId()), originPermissions);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("flag", false);
            result.put("msg", "缓存失败");
            return result;
        }
        result.put("flag", true);
        result.put("msg", "操作成功");
        return result;
    }

    @Override
    public Map<String, Object> userEmpowerment(UserForm userForm){
        Map<String, Object> result = new HashMap<>();
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(userForm, userRole);
        UserRole checkEntity = userRoleDao.selectOne(new QueryWrapper<UserRole>().lambda().eq(UserRole::getRoleId, userForm.getRoleId()).eq(UserRole::getUserId, userForm.getUserId()));
        if(checkEntity!=null){
            result.put("flag", false);
            return result;
        }
        int total = userRoleDao.insert(userRole);
        if(total != 1){
            result.put("flag", false);
            return result;
        }
        result.put("flag", true);
        result.put("userRoleId", userRole.getRoleId());
        return result;
    }


}
