package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleDao extends BaseMapper<Role> {

    Role selectByRoleName(String roleName);

    @Select(value = "SELECT ROLE_ID FROM DICT_ROLE WHERE ROLE_NAME = #{roleName}")
    int selectRoleIdByName(String roleName);

}
