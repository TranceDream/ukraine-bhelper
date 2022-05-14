package com.byb.systemservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.systemservice.Dao.SyslogDao;
import com.byb.systemservice.Entity.Objtype;
import com.byb.systemservice.Entity.SysOperation;
import com.byb.systemservice.Entity.Syslog;
import com.byb.systemservice.Service.SyslogService;
import com.byb.systemservice.Vo.SyslogForm;
import com.byb.systemservice.Vo.SyslogVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SyslogServiceImpl extends ServiceImpl<SyslogDao, Syslog> implements SyslogService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SyslogDao syslogDao;

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

    @Override
    public Map<String, Object> getSyslogList(SyslogForm syslogForm) {
        Map<String, Object> params = new HashMap<>();
        params.put("objtypeId", syslogForm.getObjtypeId());
        params.put("message", syslogForm.getMessage());
        params.put("operation", syslogForm.getOperation());
        params.put("orderText", syslogForm.getOrderText());
        params.put("start", (syslogForm.getCurrent()-1) * syslogForm.getPageSize());
        params.put("size", syslogForm.getPageSize());

        Map<String, Object> result = new HashMap<>();

        try {
            Integer total = syslogDao.countSyslogList(params);

            if (total > 0) {
                List<SyslogVo> list = syslogDao.selectSyslogList(params);
                result.put("data", list);
                result.put("current", syslogForm.getCurrent());
                result.put("pageSize", syslogForm.getPageSize());
                result.put("total", total);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
