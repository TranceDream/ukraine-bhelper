package com.byb.userservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.userservice.Entity.UserAuth;
import com.byb.userservice.Vo.UserForm;

public interface UserAuthService extends IService<UserAuth> {

    String createAccount(UserForm userForm);

    Boolean checkAccount(UserForm userForm);

}
