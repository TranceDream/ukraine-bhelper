package com.byb.security.Security;

import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Component
public class UnAuthHandle implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseUtil.out(response, new Result(Result.NO_PERMISSION, "NO AUTHORITY"));
    }
}
