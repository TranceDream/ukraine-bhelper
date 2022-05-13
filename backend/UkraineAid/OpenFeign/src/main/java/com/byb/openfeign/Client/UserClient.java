package com.byb.openfeign.Client;

import com.byb.BaseUtil.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "UserService")
public interface UserClient {

    @PostMapping("/user/getEmail")
    Result<List<String>> getEmail(@RequestBody List<Long> userIds);

    @PostMapping("/user/identify")
    Result<Map<String, Object>> identify(@RequestBody Map<String, Object> userForm);

    @PostMapping("/user/getChildGroupsSql")
    String getChildGroupsSql(@RequestParam("userId") Long userId);

    @PostMapping("/user/getOneGroup")
    Result<Map<String, Object>> getOneGroup(@RequestBody Map<String, Object> groupMap);

}
