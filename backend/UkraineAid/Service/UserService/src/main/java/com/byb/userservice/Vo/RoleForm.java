package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class RoleForm {

    private Integer roleId;

    private Date createTime;

    private String roleName;

    private String deleteMark;

    private Integer pageSize;

    private Integer pageNo;

    private Integer rolePermissionId;

    private List<Integer> permissions;

    private String lockedMark;


}
