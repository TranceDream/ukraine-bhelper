package com.byb.BaseUtil.Config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConstantConfig {
    public final static String TOKEN_HEADER = "token";

    public final static String LOGIN_PATH = "/user/login";

    public final static String REGISTER_PATH = "/user/addUserByEmail";

    public final static String ACTIVE_EMAIL_PATH = "/user/activeEmail";

    public final static String TOKEN_CHECK_URL = "http://localhost:8001/user/checkToken";

    public final static String TOKEN_CHECK_PATH = "/user/checkToken";

    public final static String REQUEST_HEADER = "url";

    public final static List<String> WHITE_LIST = new ArrayList<>();

    static {
        WHITE_LIST.add("/user/login");
        WHITE_LIST.add("/user/addUserByEmail");
        WHITE_LIST.add("/user/activeEmail");
        WHITE_LIST.add("/house/selectHouseAdmin");
        WHITE_LIST.add("/news/selectArticleForC");
        WHITE_LIST.add("/house/housedetail");
    }

    public final static String LOGIN_USER_HEADER = "loginId";

    public final static String SYSL0G_QUEUE = "syslogQueue";

    public final static String MESSAGE_QUEUE = "messageQueue";

}
