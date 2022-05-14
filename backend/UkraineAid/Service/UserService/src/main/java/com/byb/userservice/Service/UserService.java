package com.byb.userservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.userservice.Entity.Group;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Vo.GroupForm;
import com.byb.userservice.Vo.MenuVo;
import com.byb.userservice.Vo.ModuleVo;
import com.byb.userservice.Vo.UserForm;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    User getById(Long id);

    Long createAccount(UserForm userForm);

    Map<String, Object> selectUserList(UserForm userForm);

    Map<String, Object> getUserDetail(UserForm userForm);

    Map<String, Object> manageRole(UserForm userForm);

    Map<String, Object> userEmpowerment(UserForm userForm);

    Map<String, Object> getEmail(List<Long> userIds);

    Boolean identify(UserForm userForm);

    List<ModuleVo> getModuleList(Long userId);

    String getChildGroups(Long userId);

    List<GroupForm> getGroupList(Long userId);

    GroupForm getOneGroup(Long userId, Integer roleId);

    List<GroupForm> getChildGroup(Integer groupId);
}
