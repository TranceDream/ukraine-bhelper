package com.byb.openfeign.Client;

import com.byb.BaseUtil.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "auditService")
public interface AuditClient {

    @PostMapping("/audit/addAudit")
    public Result<Map<String, Object>> addAudit(@RequestBody Map<String, Object> auditForm);

}
