package com.byb.userservice.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.userservice.Dao.RoleDao;
import com.byb.userservice.Dao.RolePermissionDao;
import com.byb.userservice.Dao.UserRoleDao;
import com.byb.userservice.Entity.Permission;
import com.byb.userservice.Entity.Role;
import com.byb.userservice.Entity.RolePermission;
import com.byb.userservice.Entity.UserRole;
import com.byb.userservice.Service.RoleService;
import com.byb.userservice.Vo.PermissionForm;
import com.byb.userservice.Vo.PermissionVo;
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

    @Autowired
    private UserRoleDao userRoleDao;

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
            List<Integer> roleIds = new ArrayList<>();
            List<UserRole> userRoles = userRoleDao.selectList(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId,roleForm.getUserId()));
            for(UserRole userRole: userRoles){
                roleIds.add(userRole.getRoleId());
            }
            list = baseMapper.selectRoleList(params);
            for(RoleVo roleVo : list){
                if(roleIds.contains(roleVo.getRoleId())){
                    roleVo.setOwnMark("YES");
                }else{
                    roleVo.setOwnMark("NO");
                }
            }
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
        Map<String, Object> params = new HashMap<>();
        params.put("roleId", roleForm.getRoleId());
        try {
            List<PermissionVo> permissions = rolePermissionDao.selectPermissionList(params);
            roleVo.setPermissions(permissions);
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("data", roleVo);
        return result;
    }

    @Override
    public Map<String, Object> managePermission(RoleForm roleForm){
        Long id = Long.valueOf(roleForm.getRolePermissionId().toString());

        Map<String, Object> result = new HashMap<>();

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("lockedMark", roleForm.getLockedMark());

        int update = rolePermissionDao.updateLockedMark(params);
        if(update<=0){
            result.put("flag", false);
            result.put("msg", "操作失败");
            return result;
        }

        result.put("flag", true);
        result.put("msg", "操作成功");
        return result;
    }

    @Override
    public Map<String, Object> addRole(RoleForm roleForm) {
        Map<String, Object> result = new HashMap<>();
        Role role = new Role();
        BeanUtils.copyProperties(roleForm, role);
        try {
            baseMapper.insert(role);
        }catch (Exception e){
            e.printStackTrace();
            result.put("flag",false);
            return result;
        }
        int roleId = role.getRoleId();
        try {
            List<RolePermission> list = new ArrayList<>();
            if (roleForm.getPermissions() != null && !roleForm.getPermissions().isEmpty()) {
                for (Integer permissionId : roleForm.getPermissions()) {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(roleId);
                    rolePermission.setPermissionId(permissionId);
                    list.add(rolePermission);
                }
                Integer insertTotal = baseMapper.addList(list);
                if (insertTotal == null && insertTotal != roleForm.getPermissions().size()) {
                    result.put("flag", false);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("flag", true);
        result.put("roleId", roleId);
        return result;
    }

    @Override
    public Boolean deleteRole(RoleForm roleForm) {
        UserRole userRole = userRoleDao.selectOne(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId,roleForm.getUserId()).eq(UserRole::getRoleId,roleForm.getRoleId()));
        if(userRole != null){
            return false;
        }

        try {
            Role role = this.getById(roleForm.getRoleId());
            role.setDeleteMark("YES");
            this.updateById(role);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
