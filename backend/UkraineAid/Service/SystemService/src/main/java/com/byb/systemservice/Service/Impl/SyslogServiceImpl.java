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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SyslogServiceImpl extends ServiceImpl<SyslogDao, Syslog> implements SyslogService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long addLog(SyslogForm syslogForm) {
            Map<String, String> objtypeMap = (Map<String, String>) redisTemplate.opsForValue().get("objtype");
        String index = String.valueOf(syslogForm.getObjtypeId());
        String objtype = objtypeMap.get(index);
        if(objtype == null){
            return -1l;
        }
        Map<String, String> sysOperationMap = (Map<String, String>) redisTemplate.opsForValue().get("sysOperation");
        index = String.valueOf(syslogForm.getOperation());
        String sysOperation = sysOperationMap.get(index);
        if(sysOperation == null){
            return -1l;
        }
        Syslog syslog = new Syslog();
        BeanUtils.copyProperties(syslogForm, syslog);
        this.save(syslog);
        return syslog.getLogId();
    }
}
