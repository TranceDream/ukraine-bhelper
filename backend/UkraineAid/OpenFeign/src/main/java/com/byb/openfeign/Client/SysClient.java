package com.byb.openfeign.Client;

import com.byb.BaseUtil.Utils.Result;
import com.byb.openfeign.Fallback.SysServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "systemservice", fallbackFactory = SysServiceFallback.class)
public interface SysClient {

    @PostMapping("/sys/addLog")
    Result<Map<String, Object>> addLog(Map<String, Object> syslogForm);

    @PostMapping("/sys/launchMessage")
    Result<Map<String, Object>> launchMessage(Map<String, Object> messageForm);

    @PostMapping("/sys/getObjtypeList")
    Map<Integer, String> getObjtypeList(@RequestParam("objtypeIds") List<Integer> objtypeIds);

}
