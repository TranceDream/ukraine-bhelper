package com.byb.openfeign.Fallback;

import com.byb.BaseUtil.Utils.Result;
import com.byb.openfeign.Client.ReportClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReportServiceFallback implements FallbackFactory<ReportClient> {
    @Override
    public ReportClient create(Throwable cause) {
        return new ReportClient() {
            @Override
            public Result<Map<String, Object>> getReportList(Map<String, Object> reportForm) {
                return new Result<>(new HashMap<>(), Result.FAIL);
            }

            @Override
            public Result<Map<String, Object>> addReport(Map<String, Object> reportForm) {
                return new Result<>(new HashMap<>(), Result.FAIL);
            }

            @Override
            public Result<Map<String, Object>> doAudit(Map<String, Object> reportForm) {
                return new Result<>(new HashMap<>(), Result.FAIL);
            }
        };
    }
}
