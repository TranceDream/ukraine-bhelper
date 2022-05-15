package com.byb.openfeign.Fallback;

import com.byb.BaseUtil.Utils.Result;
import com.byb.openfeign.Client.AuditClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuditServiceFallback implements FallbackFactory<AuditClient> {
    @Override
    public AuditClient create(Throwable cause) {
        return new AuditClient() {
            @Override
            public Result<Map<String, Object>> addAudit(Map<String, Object> auditForm) {
                return new Result<>(new HashMap<>(), Result.FAIL);
            }

            @Override
            public Result<List<Object>> getReportList(Map<String, Object> auditForm) {
                return new Result<>(new ArrayList<>(), Result.FAIL);
            }
        };
    }
}
