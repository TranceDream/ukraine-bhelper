package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class PermissionVo {

    private int permissionId;

    private Date createTime;

    private String permisssionName;

    private String url;

    private List<Integer> roles;

    private int parentId;

    private String deleteMark;

}
