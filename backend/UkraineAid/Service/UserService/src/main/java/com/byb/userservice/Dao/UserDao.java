package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Vo.UserForm;
import com.byb.userservice.Vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao extends BaseMapper<User> {

//    UserVo selectOneVo(Map<String,Object> params);
    int addUser(Map<String, Object> params);

    int countUserList(Map<String, Object> params);
    List<UserVo> selectUserList(Map<String, Object> params);

    UserVo selectUserDetail(Long userId);

}
