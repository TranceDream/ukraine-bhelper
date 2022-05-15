package com.byb.openfeign.Fallback;

import com.byb.BaseUtil.Utils.Result;
import com.byb.openfeign.Client.UserClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserServiceFallback implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public Result<List<String>> getEmail(List<Long> userIds) {
                return new Result<>(null, Result.FAIL);
            }

            @Override
            public Result<Map<String, Object>> identify(Map<String, Object> userForm) {
                return new Result<>(new HashMap<>(), Result.FAIL);
            }

            @Override
            public String getChildGroupsSql(Long userId) {
                return "()";
            }

            @Override
            public Result<Map<String, Object>> getOneGroup(Map<String, Object> groupMap) {
                return new Result<>(new HashMap<>(), Result.FAIL);
            }

            @Override
            public Result<Map<Integer, String>> getChildGroup(Integer groupId) {
                return new Result<>(new HashMap<>(), Result.FAIL);
            }
        };
    }
}
