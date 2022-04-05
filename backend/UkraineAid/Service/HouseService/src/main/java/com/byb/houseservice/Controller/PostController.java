package com.byb.houseservice.Controller;

import com.byb.BaseUtil.Utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {

    @PostMapping("/test")
    public Result<Map<String, Object>> test() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "welcome to house module");
        return new Result<>(map, Result.SUCCESS);
    }

}
