package com.byb.userservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Vo.UserForm;
import com.byb.userservice.Vo.UserVo;

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
}
