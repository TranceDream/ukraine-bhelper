package com.byb.userservice.Client;

import com.alibaba.fastjson.JSONObject;
import com.byb.BaseUtil.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "systemservice")
public interface SysClient {

    @PostMapping("/sys/addLog")
    Result<Map<String, Object>> addLog(Map<String, Object> syslogForm);

}
