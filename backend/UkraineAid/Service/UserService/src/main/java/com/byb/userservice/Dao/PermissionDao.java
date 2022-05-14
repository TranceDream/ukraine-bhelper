package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.Permission;
import com.byb.userservice.Vo.ModuleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PermissionDao extends BaseMapper<Permission> {
    List<Permission> selectAll();
    List<String> selectByUser(String userId);

    Integer countPermissionList(Map<String, Object> params);
    List<ModuleVo> selectPermissionList(Map<String, Object> params);

}
