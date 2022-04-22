package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;

@Data
public class UserRoleVo {

    private Long id;
    private Long userId;
    private int roleId;
    private String roleName;
    private Date createTime;
    private String lockedMark;
    private String deleteMark;

}
