package com.byb.auditservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.auditservice.Entity.AuditOper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditOperDao extends BaseMapper<AuditOper> {
}
