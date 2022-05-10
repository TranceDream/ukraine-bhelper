package com.byb.openfeign.Client;

import com.byb.BaseUtil.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "userservice")
public interface UserClient {

    @PostMapping("/user/getEmail")
    Result<List<String>> getEmail(@RequestBody List<Long> userIds);

    @PostMapping("/user/identify")
    Result<Map<String, Object>> identify(@RequestBody Map<String, Object> userForm);

    @PostMapping("/user/getChildGroupsSql")
    String getChildGroupsSql(Long userId);

    @PostMapping("/user/getOneGroup")
    Result<Map<String, Object>> getOneGroup(Long userId, Integer roleId);

}
