package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;

@Data
public class UserVo {
    private Long userId;
    private Date createTime;
    private String country;
    private String city;
    private String identityType;
    private String identifier;
    private String credential;
}
