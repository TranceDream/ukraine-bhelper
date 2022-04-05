package com.byb.userservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

@TableName("UkraineAssistance.USER_AUTH")
@Accessors(chain = true)
@Data
public class UserAuth implements Serializable {

    @TableId(value = "AUTH_ID" , type = IdType.INPUT)
    private Long authId;

    @TableField("USER_ID")
    private Long userId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("IDENTITY_TYPE")
    private int identityType;

    @TableField("IDENTIFIER")
    private String identifier;

    @TableField("CREDENTIAL")
    private String credential;

    @TableField("IFVERIFIED")
    private String ifverified;
}
