package com.byb.userservice.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.userservice.Dao.*;
import com.byb.userservice.Entity.Group;
import com.byb.userservice.Entity.UserRole;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Service.UserService;
import com.byb.userservice.Vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

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
    private GroupDao groupDao;

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

        List<Integer> groupList = userRoleDao.selectGroupByUserId(userForm.getLoginId());
        Set<Integer> groupSet = new HashSet<>();
        for(Integer groupId : groupList) {
            List<Integer> childs = groupDao.selectGroups(groupId);
            groupSet.addAll(childs);
        }
        groupSet.add(0);
        String childGroupStr = groupSet.toString();
        childGroupStr = childGroupStr.replace("[","(");
        childGroupStr = childGroupStr.replace("]",")");
        System.out.println(childGroupStr);
        params.put("groups", childGroupStr);
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
        List<String> originPermissions = (List<String>) redisTemplate.opsForValue().get(String.valueOf(userRole.getUserId()));
        if(originPermissions != null) {
            try {
                if (userForm.getLockedMark().equals("NO")) {
                    originPermissions.addAll(permissions);
                    redisTemplate.opsForValue().set(String.valueOf(userForm.getUserId()), originPermissions);
                } else {
                    originPermissions.removeAll(permissions);
                    redisTemplate.opsForValue().set(String.valueOf(userForm.getUserId()), originPermissions);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.put("flag", false);
                result.put("msg", "缓存失败");
                return result;
            }
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

    @Override
    public Map<String, Object> getEmail(List<Long> userIds) {
        String sql = "(";
        for(int i = 0; i<userIds.size()-1; i++){
            Long id = userIds.get(i);
            sql = sql + id.toString() + ",";
        }
        Long id = userIds.get(userIds.size()-1);
        sql = sql + id + ")";

        List<String> emails = userAuthDao.selectEmails(sql);
        Map<String, Object> result = new HashMap<>();
        result.put("data", emails);
        return result;
    }

    @Override
    public Boolean identify(UserForm userForm) {
        String identityNo = userForm.getIdentityNo();
        String name = userForm.getName();
        if(identityNo.length()!=17 && identityNo.length()!=15){
            return false;
        }
        try {
            User user = baseMapper.selectById(userForm.getUserId());
            user.setName(name);
            user.setIdentityNo(identityNo);
            user.setIfverified("YES");
            this.updateById(user);
            UserRole userRole = new UserRole();
            userRole.setUserId(userForm.getUserId());
            userRole.setRoleId(10003);
            userRole.setGroupId(10005);
            userRoleDao.insert(userRole);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<ModuleVo> getModuleList(Long userId) {
        List<ModuleVo> list = new ArrayList<>();
        try {
            list = baseMapper.selectModuleList(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String getChildGroups(Long userId) {
        List<Integer> groupList = userRoleDao.selectGroupByUserId(userId);
        Set<Integer> groupSet = new HashSet<>();
        for(Integer groupId : groupList) {
            List<Integer> childs = groupDao.selectGroups(groupId);
            groupSet.addAll(childs);
        }
        String childGroupStr = groupSet.toString();
        childGroupStr = childGroupStr.replace("[","(");
        childGroupStr = childGroupStr.replace("]",")");
        return childGroupStr;
    }

    @Override
    public List<GroupForm> getGroupList(Long userId) {
        List<Integer> groupList = userRoleDao.selectGroupByUserId(userId);
        Set<GroupForm> groupSet = new HashSet<>();
        for(Integer groupId : groupList) {
            List<GroupForm> childs = groupDao.selectGroupVos(groupId);
            groupSet.addAll(childs);
        }
        List<GroupForm> result = new ArrayList<>();
        result.addAll(groupSet);
        result = sortGroup(result, -1);
        return result;
    }

    @Override
    public List<GroupForm> getChildGroup(Integer groupId){
        List<GroupForm> childs = groupDao.selectGroupVos(groupId);
        return childs;
    }

    @Override
    public GroupForm getOneGroup(Long userId, Integer roleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("roleId", roleId);
        GroupForm groupForm = groupDao.selectOneGroup(params);
        return groupForm;
    }

    private List<GroupForm> sortGroup(List<GroupForm> origin, int originParentId){
        List<GroupForm> result = new ArrayList<>();
        if(origin.size() == 1){
            if(origin.get(0).getParentId() == originParentId) {
                return origin;
            }
            else {
                return null;
            }
        }
        for(GroupForm groupForm : origin){
            int parentId = groupForm.getParentId();
            int groupId = groupForm.getGroupId();
            boolean flag = true;
            try {
                for (GroupForm groupForm1 : origin) {
                    if (parentId == groupForm1.getGroupId() && groupId != groupForm1.getGroupId()) {
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    continue;
                }
                if (parentId == originParentId || originParentId == -1) {
                    result.add(groupForm);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        origin.removeAll(result);
        if(origin.size() > 0) {
            for (GroupForm groupForm : result) {
                groupForm.setChilds(sortGroup(origin, groupForm.getGroupId()));
            }
        }
        return result;
    }

//    @Override
//    public List<MenuVo> getMenuList(Long userId) {
//        List<MenuVo> list = baseMapper.selectMenuList(userId);
//        return list;
//    }


}
