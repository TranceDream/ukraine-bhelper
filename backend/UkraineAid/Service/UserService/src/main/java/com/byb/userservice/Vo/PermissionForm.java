package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class PermissionForm {

    private Integer permissionId;

    private Date createTime;

    private String roleId;

    private String permissionName;

    private String url;

    private String icon;

    private String pagekey;

    private List<Integer> roles;

    private Integer parentId;

    private String deleteMark;

    private Integer pageSize;

    private Integer pageNo;

    private String orderText;
}
