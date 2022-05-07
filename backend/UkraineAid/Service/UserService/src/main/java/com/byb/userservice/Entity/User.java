package com.byb.userservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@TableName("UA.USER")
@Accessors(chain = true)
@Data
public class User implements Serializable {

    @TableId(value = "USER_ID", type = IdType.AUTO)
    private Long userId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("COUNTRY")
    private String country;

    @TableField("CITY")
    private String city;

    @TableField("NAME")
    private String name;

    @TableField("IDENTITY_NO")
    private String identityNo;

    @TableField("IFVERIFIED")
    private String ifverified;

    @TableField("DELETE_MARK")
    private String deleteMark;
}
