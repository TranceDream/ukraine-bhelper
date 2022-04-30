package com.byb.userservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.userservice.Dao.PermissionDao;
import com.byb.userservice.Dao.RoleDao;
import com.byb.userservice.Entity.Permission;
import com.byb.userservice.Entity.RolePermission;
import com.byb.userservice.Service.PermissionService;
import com.byb.userservice.Vo.PermissionForm;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        Map<String, Object> params = new HashMap<>();
        params.put("start", (permissionForm.getPageNo()-1) * permissionForm.getPageSize());
        params.put("size", permissionForm.getPageSize());
        params.put("permissionId", permissionForm.getPermissionId());
        params.put("permissionName", permissionForm.getPermissionName());
        params.put("deleteMark", permissionForm.getDeleteMark());
        params.put("orderText", permissionForm.getOrderText());
        params.put("url", permissionForm.getUrl());

        Map<String, Object> result = new HashMap<>();
        List<Permission> list = new ArrayList<>();

        Integer total = baseMapper.countPermissionList(params);
        try {
            if (total > 0) {
                list = baseMapper.selectPermissionList(params);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("total", total);
        result.put("data", list);
        result.put("pageSize", permissionForm.getPageSize());
        result.put("pageNo", permissionForm.getPageNo());
        return result;
    }

    @Override
    public Map<String, Object> addPermission(PermissionForm permissionForm) {

        Map<String, Object> result = new HashMap<>();

        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionForm, permission);
        try {
            baseMapper.insert(permission);
        }catch (Exception e){
            e.printStackTrace();
        }
        Integer permissionId = permission.getPermissionId();

        List<RolePermission> list = new ArrayList<>();
        if(permissionForm.getRoles()!=null && !permissionForm.getRoles().isEmpty()){
            for(Integer roleId : permissionForm.getRoles()){
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                list.add(rolePermission);
            }

            Integer total = roleDao.addList(list);
            if(total != permissionForm.getRoles().size()){
                result.put("flag", false);
                return result;
            }
        }
        result.put("flag", true);
        result.put("permissionId", permissionId);
        return result;
    }

}
