package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionDao extends BaseMapper<Permission> {
    List<Permission> selectAll();
    List<String> selectByUser(String userId);
}
