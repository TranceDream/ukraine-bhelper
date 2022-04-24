package com.byb.systemservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.systemservice.Dao.SyslogDao;
import com.byb.systemservice.Entity.Objtype;
import com.byb.systemservice.Entity.SysOperation;
import com.byb.systemservice.Entity.Syslog;
import com.byb.systemservice.Service.SyslogService;
import com.byb.systemservice.Vo.SyslogForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SyslogServiceImpl extends ServiceImpl<SyslogDao, Syslog> implements SyslogService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long addLog(SyslogForm syslogForm) {
        Map<Integer, String> objtypeMap = (Map<Integer, String>) redisTemplate.opsForValue().get("objtype");
        String objtype = objtypeMap.get(syslogForm.getObjtypeId());
        if(objtype == null){
            return -1l;
        }
        Map<Integer, String> sysOperationMap = (Map<Integer, String>) redisTemplate.opsForValue().get("sysOperation");
        String sysOperation = sysOperationMap.get(syslogForm.getOperator());
        if(sysOperation == null){
            return -1l;
        }
        Syslog syslog = new Syslog();
        BeanUtils.copyProperties(syslogForm, syslog);
        this.save(syslog);
        return syslog.getLogId();
    }
}
