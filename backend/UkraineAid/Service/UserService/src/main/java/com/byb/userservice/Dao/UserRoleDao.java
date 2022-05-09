package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.UserRole;
import com.byb.userservice.Vo.UserRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserRoleDao extends BaseMapper<UserRole> {

    @Update(value = "update DICT_USER_ROLE set LOCKED_MARK = #{lockMark} where ID = #{userRoleId}")
    int updateLockedMark(Map<String, Object> params);

    @Select(value = "select dp.URL from DICT_ROLE_PERMISSION drp left join DICT_PERMISSION dp on drp.PERMISSION_ID = dp.PERMISSION_ID where drp.ROLE_ID = #{roleId} and drp.DELETE_MARK = 'NO' and dp.DELETE_MARK = 'NO'")
    List<String> selectUrlByRoleId(Map<String, Object> params);

    @Select(value = "select GROUP_ID from DICT_USER_ROLE where USER_ID = #{userId} and LOCKED_MARK = 'NO' and DELETE_MARK = 'NO'")
    List<Integer> selectGroupByUserId(Long userId);
}
