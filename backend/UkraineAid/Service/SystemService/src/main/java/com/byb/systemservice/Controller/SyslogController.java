package com.byb.systemservice.Controller;

import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.systemservice.Service.SyslogService;
import com.byb.systemservice.Vo.SyslogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class SyslogController {

    @Autowired
    private SyslogService syslogService;

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

}
