package com.byb.userservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

@TableName("UkraineAssistance.DICT_USER_ROLE")
@Accessors(chain = true)
@Data
public class UserRole implements Serializable {

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("USER_ID")
    private Long userId;

    @TableField("ROLE_ID")
    private int roleId;

    @TableField("DELETE_MARK")
    private String deleteMark;

    @TableField("LOCKED_MARK")
    private String lockedMark;

}
