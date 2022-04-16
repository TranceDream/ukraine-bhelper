package com.byb.BaseUtil.Utils;

import java.util.UUID;

public class UUIDUtils {

    public static String createUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }

}
