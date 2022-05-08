package com.byb.userservice.Vo;

import com.byb.userservice.Entity.RolePermission;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class RoleVo {

    private int roleId;

    private Date createTime;

    private String roleName;

    private String deleteMark;

    private List<PermissionVo> permissions;

}
