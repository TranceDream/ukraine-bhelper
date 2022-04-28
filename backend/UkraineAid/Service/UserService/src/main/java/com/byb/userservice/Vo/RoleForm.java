package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;

@Data
public class RoleForm {

    private int roleId;

    private Date createTime;

    private String roleName;

    private String deleteMark;

    private Integer pageSize;

    private Integer pageNo;


}
