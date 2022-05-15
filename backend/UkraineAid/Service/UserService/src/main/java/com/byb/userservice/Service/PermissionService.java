package com.byb.userservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.userservice.Entity.Permission;
import com.byb.userservice.Vo.PermissionForm;

import java.util.List;
import java.util.Map;

public interface PermissionService extends IService<Permission> {

    Map<String, Object> getPermissionList(PermissionForm permissionForm);

    Map<String, Object> addPermission(PermissionForm permissionForm);

    List<Permission> getPermission4Role(PermissionForm permissionForm);

}
