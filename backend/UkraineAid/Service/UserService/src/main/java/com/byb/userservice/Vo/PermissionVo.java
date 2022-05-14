package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class PermissionVo {

    private int rolePermissionId;

    private Integer permissionId;

    private Date createTime;

    private String permissionName;

    private String url;

    private List<Integer> roles;

    private Integer parentId;

    private String lockedMark;

    private String deleteMark;

}
