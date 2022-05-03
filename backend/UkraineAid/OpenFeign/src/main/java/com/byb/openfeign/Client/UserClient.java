package com.byb.openfeign.Client;

import com.byb.BaseUtil.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "userservice")
public interface UserClient {

    @PostMapping("/user/getEmail")
    Result<List<String>> getEmail(@RequestBody List<Long> userIds);

}
