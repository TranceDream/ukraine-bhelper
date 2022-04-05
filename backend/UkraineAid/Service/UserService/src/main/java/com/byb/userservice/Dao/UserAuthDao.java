package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.UserAuth;
import com.byb.userservice.Vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserAuthDao extends BaseMapper<UserAuth> {

    UserVo selectOneVo(Map<String, Object> params);
}
