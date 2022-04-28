package com.byb.userservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.userservice.Entity.Role;
import com.byb.userservice.Vo.RoleForm;
import com.byb.userservice.Vo.RoleVo;

import java.util.List;
import java.util.Map;

public interface RoleService extends IService<Role> {

     Map<String, Object> getRoleList(RoleForm roleForm);

     Map<String, Object> getRoleDetail(RoleForm roleForm);

}
