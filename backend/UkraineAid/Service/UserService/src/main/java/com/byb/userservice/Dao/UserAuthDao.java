package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.UserAuth;
import com.byb.userservice.Vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserAuthDao extends BaseMapper<UserAuth> {

    UserVo selectOneVo(Map<String, Object> params);

    @Select(value = "select TYPE_ID from DICT_IDENTITY_TYPE WHERE TYPE_NAME = #{type}")
    int selectIdentityType(String type);

    List<String> selectEmails(String scope);

    @Update(value = "update DICT_USER_AUTH set CREDENTIAL = #{pwd} where USER_ID = #{userId} and IDENTITY_TYPE = 10001")
    Integer updatePwd(Map<String, Object> params);

}
