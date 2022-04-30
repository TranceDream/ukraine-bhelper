package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface RolePermissionDao extends BaseMapper<RolePermission> {

    @Update(value = "update DICT_ROLE_PERMISSION set LOCKED_MARK = #{lockedMark} where ID = #{id}")
    Integer updateLockedMark(Map<String, Object> params);


}
