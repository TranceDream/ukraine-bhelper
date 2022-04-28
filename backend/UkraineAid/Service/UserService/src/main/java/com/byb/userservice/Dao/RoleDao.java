package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.Role;
import com.byb.userservice.Vo.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleDao extends BaseMapper<Role> {

    Role selectByRoleName(String roleName);

    @Select(value = "SELECT ROLE_ID FROM DICT_ROLE WHERE ROLE_NAME = #{roleName}")
    int selectRoleIdByName(String roleName);

    int countRoleList(Map<String, Object> params);
    List<RoleVo> selectRoleList(Map<String, Object> params);

}
