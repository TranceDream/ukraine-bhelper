package com.byb.openfeign.Client;

import com.byb.BaseUtil.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "AuditService")
public interface AuditClient {

    @PostMapping("/audit/addAudit")
    public Result<Map<String, Object>> addAudit(@RequestBody Map<String, Object> auditForm);

    @PostMapping("/audit/getAuditList")
    public Result<List<Object>> getReportList(@RequestBody Map<String, Object> auditForm);

}
