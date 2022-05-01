package com.byb.auditservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.auditservice.Entity.Status;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatusDao extends BaseMapper<Status> {
}
