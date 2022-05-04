package com.byb.userservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UA.MENU")
@Accessors(chain = true)
@Data
public class Menu {

    @TableId(value = "MENU_ID", type = IdType.AUTO)
    private int permissionId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MENU")
    private String permissionName;

    @TableField("URL")
    private String url;

    @TableField("DELETE_MARK")
    private String deleteMark;

}
