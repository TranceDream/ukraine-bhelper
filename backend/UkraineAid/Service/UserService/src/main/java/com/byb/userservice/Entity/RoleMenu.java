package com.byb.userservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UA.DICT_ROLE_MENU")
@Accessors(chain = true)
@Data
public class RoleMenu {

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("ROLE_ID")
    private Integer roleId;

    @TableField("MENU_ID")
    private Integer menuId;

    @TableField("DELETE_MARK")
    private String deleteMark;

}
