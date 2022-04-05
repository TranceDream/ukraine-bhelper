package com.byb.userservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.userservice.Entity.User;

public interface UserService extends IService<User> {

    User getById(Long id);
}
