package com.byb.userservice.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.userservice.Dao.RoleDao;
import com.byb.userservice.Dao.RolePermissionDao;
import com.byb.userservice.Entity.Permission;
import com.byb.userservice.Entity.Role;
import com.byb.userservice.Entity.RolePermission;
import com.byb.userservice.Service.RoleService;
import com.byb.userservice.Vo.RoleForm;
import com.byb.userservice.Vo.RoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public Map<String, Object> getRoleList(RoleForm roleForm) {
        Map<String, Object> params = new HashMap<>();
        params.put("roleId", roleForm.getRoleId());
        params.put("roleName", roleForm.getRoleName());
        params.put("deleteMark", roleForm.getDeleteMark());
        params.put("start", (roleForm.getPageNo()-1) * roleForm.getPageSize());
        params.put("size", roleForm.getPageSize());

        Map<String, Object> result = new HashMap<>();
        List<RoleVo> list = new ArrayList<>();
        int total = baseMapper.countRoleList(params);
        if(total > 0){
            list = baseMapper.selectRoleList(params);
        }
        result.put("total", total);
        result.put("data", list);
        result.put("pageNo", roleForm.getPageNo());
        result.put("pageSize", roleForm.getPageSize());
        return result;
    }

    @Override
    public Map<String, Object> getRoleDetail(RoleForm roleForm) {
        Map<String, Object> result = new HashMap<>();
        Role role = this.getById(roleForm.getRoleId());
        if(role == null || role.getDeleteMark().equals("YES")){
            return null;
        }
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);
        List<RolePermission> permissions = rolePermissionDao.selectList(new QueryWrapper<RolePermission>().lambda().eq(RolePermission::getRoleId, roleForm.getRoleId()).eq(RolePermission::getDeleteMark, "NO"));
        roleVo.setPermissions(permissions);
        result.put("data", roleVo);
        return result;
    }

}
