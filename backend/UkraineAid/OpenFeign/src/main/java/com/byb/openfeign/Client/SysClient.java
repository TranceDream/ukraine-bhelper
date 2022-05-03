package com.byb.openfeign.Client;

import com.byb.BaseUtil.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "systemservice")
public interface SysClient {

    @PostMapping("/sys/addLog")
    Result<Map<String, Object>> addLog(Map<String, Object> syslogForm);

}
