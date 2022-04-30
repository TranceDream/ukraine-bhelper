package com.byb.userservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

@TableName("UA.DICT_ROLE_PERMISSION")
@Accessors(chain = true)
@Data
public class RolePermission implements Serializable {

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("PERMISSION_ID")
    private int permissionId;

    @TableField("ROLE_ID")
    private int roleId;

    @TableField("DELETE_MARK")
    private String deleteMark;

    @TableField("LOCKED_MARK")
    private String lockedMark;
}
