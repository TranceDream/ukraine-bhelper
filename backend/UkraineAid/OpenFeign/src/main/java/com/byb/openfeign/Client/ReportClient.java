package com.byb.openfeign.Client;

import com.byb.BaseUtil.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "reportservice")
public interface ReportClient {

    @GetMapping("/report/getReportList")
    public Result<Map<String, Object>> getReportList(@RequestBody Map<String, Object> reportForm);

    @PostMapping("/report/addReport")
    public Result<Map<String, Object>> addReport(@RequestBody Map<String, Object> reportForm);

    @PostMapping("/report/doAudit")
    public Result<Map<String, Object>> doAudit(@RequestBody Map<String, Object> reportForm);

}