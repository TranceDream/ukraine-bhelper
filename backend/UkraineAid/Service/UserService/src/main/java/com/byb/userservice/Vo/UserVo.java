package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class UserVo {
    private Long userId;
    private Date createTime;
    private String country;
    private String city;
    private String identityType;
    private String identifier;
    private String credential;
    private String name;
    private String identityNo;

    private String ifverified;

    private int roleId;
    private String roleName;

    private int permissionId;
    private String permissionUrl;

    private List<UserRoleVo> roleList;
    private List<String> permissionList;
}
