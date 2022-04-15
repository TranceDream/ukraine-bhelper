package com.byb.userservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Vo.UserForm;

public interface UserService extends IService<User> {

    User getById(Long id);

    Long createAccount(UserForm userForm);
}
