package com.byb.userservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.userservice.Entity.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuDao extends BaseMapper<Menu> {
}
