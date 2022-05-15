package com.byb.userservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UA.DICT_MENU")
@Accessors(chain = true)
@Data
public class Menu {

    @TableId(value = "MENU_ID", type = IdType.AUTO)
    private int menuId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("MENU")
    private String menu;

    @TableField("URL")
    private String url;

    @TableField("ICON")
    private String icon;

    @TableField("DEFAULT_MARK")
    private String defaultMark;

    @TableField("DELETE_MARK")
    private String deleteMark;

}
