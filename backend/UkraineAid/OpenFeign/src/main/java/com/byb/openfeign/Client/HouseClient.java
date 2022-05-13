package com.byb.openfeign.Client;

import com.byb.BaseUtil.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "HouseService")
public interface HouseClient {

    @PostMapping("/deleteinfo")
    public Result<Map<String ,Object>> deleteHouse(@RequestBody Map<String, Object> ma);

}
