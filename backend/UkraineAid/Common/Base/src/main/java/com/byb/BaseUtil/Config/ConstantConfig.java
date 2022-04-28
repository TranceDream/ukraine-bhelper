package com.byb.BaseUtil.Config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ConstantConfig {
    public final static String TOKEN_HEADER = "token";

    public final static String LOGIN_PATH = "/user/login";

    public final static String TOKEN_CHECK_URL = "http://localhost:8001/user/checkToken";

    public final static String TOKEN_CHECK_PATH = "/user/checkToken";

    public final static String REQUEST_HEADER = "url";

    public final static String LOGIN_USER_HEADER = "loginId";

}
