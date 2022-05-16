package com.byb.userservice.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.userservice.Dao.PermissionDao;
import com.byb.userservice.Dao.RoleDao;
import com.byb.userservice.Entity.Permission;
import com.byb.userservice.Entity.RolePermission;
import com.byb.userservice.Service.PermissionService;
import com.byb.userservice.Vo.ModuleVo;
import com.byb.userservice.Vo.PermissionForm;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Map<String, Object> getPermissionList(PermissionForm permissionForm) {
        List<ModuleVo> list = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        try {
                list = baseMapper.selectPermissionList(new HashMap<>());
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("data", list);
        return result;
    }

    @Override
    public Map<String, Object> addPermission(PermissionForm permissionForm) {

        Map<String, Object> result = new HashMap<>();

        Permission permission = new Permission();
        try {
            BeanUtils.copyProperties(permissionForm, permission);
            baseMapper.insert(permission);
            Integer permissionId = permission.getPermissionId();

            result.put("flag", true);
            result.put("permissionId", permissionId);
        }catch (Exception e){
            e.printStackTrace();
            result.put("flag", false);
            return result;
        }
        return result;
    }

    @Override
    public List<Permission> getPermission4Role(PermissionForm permissionForm) {

        List<Permission> temp = baseMapper.selectList(new QueryWrapper<Permission>().lambda().eq(Permission::getDeleteMark, "NO"));
        List<Permission> result = new ArrayList<>();
        for(Permission permission : temp){
            if(permission.getParentId() != 1 && permission.getParentId() != 0){
                result.add(permission);
            }
        }
        return result;
    }

}
