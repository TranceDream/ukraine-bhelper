package com.byb.systemservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.systemservice.Entity.Syslog;
import com.byb.systemservice.Vo.SyslogForm;
import com.byb.systemservice.Vo.SyslogVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SyslogDao extends BaseMapper<Syslog> {

    List<SyslogVo> selectSyslogList(Map<String, Object> params);

    Integer countSyslogList(Map<String, Object> params);

}
