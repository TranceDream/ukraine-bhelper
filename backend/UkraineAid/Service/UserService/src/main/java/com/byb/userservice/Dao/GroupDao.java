package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.Group;
import com.byb.userservice.Vo.GroupForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupDao extends BaseMapper<Group> {

    List<Integer> selectGroups(Integer groupId);

    List<GroupForm> selectGroupVos(Integer groupId);

    GroupForm selectOneGroup(Map<String, Object> params);
}
