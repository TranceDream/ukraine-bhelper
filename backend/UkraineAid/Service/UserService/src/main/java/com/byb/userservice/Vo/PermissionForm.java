package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class PermissionForm {

    private int permissionId;

    private Date createTime;

    private String permissionName;

    private String url;

    private List<Integer> roles;

    private String deleteMark;

    private Integer pageSize;

    private Integer pageNo;

    private String orderText;
}
