package com.byb.security.Controller;

import com.byb.BaseUtil.Utils.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({AccessDeniedException.class})
    public Result<Map<String, Object>> handlerException(){
        return new Result<>(null, Result.NO_PERMISSION, "NO PERMISSION");
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<Map<String, Object>> runtimeHandlerException(){
        return new Result<>(null, Result.FAIL, "SERVER ERROR");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Result<Map<String, Object>> usernameNotFoundException(){
        System.out.println("usernameNotFoundException");
        return new Result<>(null, Result.NO_PERMISSION, "TOKEN ERROR");
    }

}
