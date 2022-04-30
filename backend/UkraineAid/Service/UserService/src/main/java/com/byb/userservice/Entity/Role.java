package com.byb.userservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

@TableName("UA.DICT_ROLE")
@Accessors(chain = true)
@Data
public class Role implements Serializable {
    @TableId(value = "ROLE_ID", type = IdType.AUTO)
    private Integer roleId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("ROLE_NAME")
    private String roleName;

    @TableField("DELETE_MARK")
    private String deleteMark;
}
