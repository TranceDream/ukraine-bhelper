package com.byb.auditservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.auditservice.Entity.Audit;
import com.byb.auditservice.Vo.AuditVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AuditDao extends BaseMapper<Audit> {

    List<AuditVo> selectAuditList(Map<String, Object> params);

}
