package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;

@Data
public class UserForm {
    private Long userId;
    private Date createTime;
    private String country;
    private String city;
    private String identifier;
    private String credential;
    private int identityType;
    private String uuid;
}
