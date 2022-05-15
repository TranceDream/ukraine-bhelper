package com.byb.openfeign.Fallback;

import com.byb.BaseUtil.Utils.Result;
import com.byb.openfeign.Client.SysClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SysServiceFallback implements FallbackFactory<SysClient> {


    @Override
    public SysClient create(Throwable cause) {
        return new SysClient() {
            @Override
            public Result<Map<String, Object>> addLog(Map<String, Object> syslogForm) {
                return new Result<>(new HashMap<>(), Result.FAIL,"添加失败");
            }

            @Override
            public Result<Map<String, Object>> launchMessage(Map<String, Object> messageForm) {
                return new Result<>(new HashMap<>(), Result.FAIL, "发送失败");
            }

            @Override
            public Map<Integer, String> getObjtypeList(List<Integer> objtypeIds) {
                return new HashMap<>();
            }
        };
    }
}
