package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserDao extends BaseMapper<User> {

//    UserVo selectOneVo(Map<String,Object> params);

}
