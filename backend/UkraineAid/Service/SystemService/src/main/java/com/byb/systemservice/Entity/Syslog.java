package com.byb.systemservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UA.SYSLOG")
@Accessors(chain = true)
@Data
public class Syslog {

    @TableId(value = "LOG_ID", type = IdType.AUTO)
    private Long logId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("DELETE_MARK")
    private String deleteMark;

    @TableField("OBJTYPE_ID")
    private int objtypeId;

    @TableField("OBJ_ID")
    private Long objId;

    @TableField("OPERATOR")
    private Long operator;

    @TableField("OPERATION")
    private int operation;

    @TableField("MESSAGE")
    private String message;

}
