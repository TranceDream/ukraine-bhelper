package com.byb.systemservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.systemservice.Entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageDao extends BaseMapper<Message> {
}
