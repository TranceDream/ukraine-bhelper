package com.byb.systemservice.Config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byb.systemservice.Dao.ObjtypeDao;
import com.byb.systemservice.Dao.SysOperationDao;
import com.byb.systemservice.Entity.Objtype;
import com.byb.systemservice.Entity.SysOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Dict {

    @Autowired
    private ObjtypeDao objtypeDao;

    @Autowired
    private SysOperationDao sysOperationDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    private void loadDB(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        List<Objtype> objtypeList = objtypeDao.selectList(new QueryWrapper<Objtype>().lambda().eq(Objtype::getDeleteMark, "NO"));
        List<SysOperation> sysOperationList = sysOperationDao.selectList(new QueryWrapper<SysOperation>().lambda().eq(SysOperation::getDeleteMark, "NO"));
        Map<Integer, String> objtypeMap = new HashMap<>();
        for(Objtype objtype : objtypeList){
            objtypeMap.put(objtype.getObjtypeId(), objtype.getObjtype());
        }
        Map<Integer, String> sysOperationMap = new HashMap<>();
        for(SysOperation sysOperation : sysOperationList){
            sysOperationMap.put(sysOperation.getOperId(), sysOperation.getOper());
        }
        redisTemplate.opsForValue().set("objtype", objtypeMap);
        redisTemplate.opsForValue().set("sysOperation", sysOperationMap);
    }

}
