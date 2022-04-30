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

    private Integer roleId;
    private String roleName;

    private int permissionId;
    private String permissionUrl;

    private Long userRoleId;

    private String lockedMark;

    private String orderText;

    private int pageSize;
    private int pageNo;
}
