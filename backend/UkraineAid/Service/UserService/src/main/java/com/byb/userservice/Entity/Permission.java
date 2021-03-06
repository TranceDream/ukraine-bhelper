package com.byb.userservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

@TableName("UA.DICT_PERMISSION")
@Accessors(chain = true)
@Data
public class Permission implements Serializable {

    @TableId(value = "PERMISSION_ID", type = IdType.AUTO)
    private Integer permissionId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("PERMISSION_NAME")
    private String permissionName;

    @TableField("URL")
    private String url;

    @TableField("PARENT_ID")
    private Integer parentId;

    @TableField("ICON")
    private String icon;

    @TableField("PAGEKEY")
    private String pagekey;

    @TableField("DELETE_MARK")
    private String deleteMark;
}
