package com.byb.systemservice.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.systemservice.Dao.SysOperationDao;
import com.byb.systemservice.Entity.SysOperation;
import com.byb.systemservice.Service.SyslogService;
import com.byb.systemservice.Vo.SyslogForm;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class SyslogController {

    @Autowired
    private SyslogService syslogService;

    @Autowired
    private SysOperationDao sysOperationDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/addLog")
    public Result<Map<String, Object>> addLog(@RequestBody SyslogForm syslogForm){
        if(syslogForm.getObjId()==null){
            return new Result(null, Result.FAIL);
        }
        Long logId = syslogService.addLog(syslogForm);
        if(logId.equals(-1l)){
            return new Result(null, Result.FAIL);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("logId", logId);
        return new Result<>(map, Result.SUCCESS);
    }

    @PostMapping("/addLogtest")
    public Result<Map<String, Object>> addLogtest(@RequestBody SyslogForm syslogForm){
        System.out.println(syslogForm);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "addlog");
        return new Result<>(map, Result.SUCCESS);
    }

    @PostMapping("/getObjtypeList")
    public Map<Integer, String> getObjtypeList(@RequestParam("objtypeIds") List<Integer> objtypeIds){
        Map<Integer, String> result = new HashMap<>();
        Map<Integer, String> objtypeMap = (Map<Integer, String>) redisTemplate.opsForValue().get("objtype");
        for (Integer objtypeId : objtypeIds){
            result.put(objtypeId, objtypeMap.get(objtypeId.toString()));
        }
        return result;
    }

    @PostMapping("/getAdminObjtypeList")
    public Result<Map<Integer, String>> getAdminObjtypeList(){
        Map<Integer, String> objtypeMap = (Map<Integer, String>) redisTemplate.opsForValue().get("objtype");
        return new Result<>(objtypeMap, Result.SUCCESS);
    }

    @PostMapping("/getSyslogList")
    public Result<Map<String, Object>> getSyslogList(@RequestBody SyslogForm syslogForm, HttpServletResponse response){
        if(syslogForm.getObjtypeId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "类别id为空"));
        }

        if(syslogForm.getCurrent() == null){
            syslogForm.setCurrent(1);
        }

        if(syslogForm.getPageSize() == null){
            syslogForm.setPageSize(20);
        }

        Map<String, Object> dataMap = syslogService.getSyslogList(syslogForm);

        return new Result<>(dataMap, Result.SUCCESS);
    }

    @PostMapping("/getOperationList")
    public Result<List<SysOperation>> getOperationList(){
        List<SysOperation> list = sysOperationDao.selectList(new QueryWrapper<SysOperation>().lambda().eq(SysOperation::getDeleteMark, "NO"));
        return new Result<>(list, Result.SUCCESS);
    }

}
